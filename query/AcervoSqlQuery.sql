CREATE DATABASE AcervoBD
use AcervoBD

INSERT INTO aluno VALUES ('23659060876', 22202221, 'Lucas Yoshihiro Shiroma Malpartida', 'Lucas.Malpartida1', '111')

CREATE TABLE aluno(
	cpf 			CHAR(11),
	ra				CHAR(11),
	nome			VARCHAR(100),
	email			VARCHAR(100),
	senha			VARCHAR(100),
	PRIMARY KEY(cpf)
)

CREATE TABLE exemplar(
	codigo			VARCHAR(13),
	nome			VARCHAR(150),
	qtdPaginas		INT
	PRIMARY KEY(codigo)
)

CREATE TABLE locacao(
	codigo			INT,
	alunoCpf		CHAR(11),
	exemplarCodigo	VARCHAR(13),
	dataRetirada	DATE,
	qtdDiasAluguel	INT
	PRIMARY KEY(codigo)
	FOREIGN KEY (alunoCpf) REFERENCES aluno(cpf),
	FOREIGN KEY(exemplarCodigo) REFERENCES exemplar(codigo)
)

CREATE TABLE livro(
	ISBN			VARCHAR(13)
	PRIMARY KEY(ISBN)
	FOREIGN KEY(ISBN) REFERENCES exemplar(codigo)
)

CREATE TABLE revista(
	ISSN			VARCHAR(13)
	PRIMARY KEY(ISSN)
	FOREIGN KEY(ISSN) REFERENCES exemplar(codigo)
)

CREATE PROCEDURE sp_inserirAluno(@cpf CHAR(11), @nome VARCHAR(150), @senha VARCHAR(100), @saida VARCHAR(200) OUTPUT)
AS
	DECLARE @email VARCHAR(100)
	DECLARE @codigo INT
	DECLARE @CPFvalido BIT
	DECLARE @ra CHAR(10)
	DECLARE @senhaValido BIT

	IF EXISTS(SELECT 1 FROM aluno WHERE cpf = @cpf)
	BEGIN 
		SET @saida = 'cpf ja cadastrado'
		RETURN 
	END
	ELSE
	BEGIN
		EXEC sp_validarCpf @cpf, @CPFvalido OUTPUT
		IF (@CPFvalido = 0)
		BEGIN
			SET @saida = 'cpf Invalido'
			RETURN
		END
	END

	EXEC sp_validarSenha @senha, @senhaValido OUTPUT

	IF @senhaValido = 0
	BEGIN
		SET @saida = 'Senha Invalida'
		RETURN 
	END

	EXEC sp_criarRa @ra OUTPUT
	EXEC sp_criarEmail @nome, @email OUTPUT
	INSERT INTO aluno VALUES(@cpf,@ra, @nome, @email, @senha)



CREATE PROCEDURE sp_validarCpf (@cpf CHAR(11), @valido BIT OUTPUT)
AS
	DECLARE @naoigual BIT, @valido_Pdigito BIT, @valido_Sdigito BIT
	SET @valido = 0
	EXEC sp_verificarIgualdade @cpf, @naoigual OUTPUT
	IF(@naoigual = 1)
	BEGIN
		EXEC sp_primeiroDigito @cpf, @valido_Pdigito OUTPUT
		EXEC sp_segundoDigito  @cpf, @valido_Sdigito OUTPUT
		IF (@valido_Pdigito = 1 AND @valido_Sdigito = 1)
		BEGIN
			SET @valido = 1
		END
	END

CREATE PROCEDURE sp_verificarIgualdade(@cpf CHAR(11), @valido BIT OUTPUT)
AS
	DECLARE @valor1 CHAR(1), @valor2 CHAR(2), @cta INT = 1
	set @valor2 = SUBSTRING(@cpf, 1, 1)
	WHILE(@cta <=11)
	BEGIN
		SET @valor1 = SUBSTRING(@cpf, @cta, 1)
		IF(@valor1 != @valor2)
		BEGIN
			SET @valido = 1;
			SET @cta = 11;
		END
		SET @valor2 = @valor1;
		SET @cta = @cta + 1;
	END

CREATE PROCEDURE sp_primeiroDigito @cpf CHAR(11), @valido_Pdigito BIT OUTPUT
AS
	DECLARE @cta INT = 1, @soma INT = 0, @resto INT,@verificador CHAR(1), @multiplicador INT = 10
	WHILE(@cta < 10)
	BEGIN
		SET @soma = @soma + (SUBSTRING(@cpf, @cta, 1) * @multiplicador)
		SET @cta = @cta + 1
		SET @multiplicador = @multiplicador - 1;
	END
	
	SET @resto = (@soma %11)
	IF(@resto < 2)
	BEGIN
		SET @verificador = '0';
	END
	ELSE
	BEGIN
		SET @verificador = CAST((11 - @resto) AS CHAR(1))
	END

	IF (@verificador = SUBSTRING(@cpf, 10, 1))
	BEGIN
		SET @valido_Pdigito = 1
	END
	ELSE
	BEGIN
		SET @valido_Pdigito = 0
	END


CREATE PROCEDURE sp_segundoDigito @cpf CHAR(11), @valido_Sdigito BIT OUTPUT
AS
	DECLARE @cta INT = 1, @soma INT = 0, @resto INT,@verificador CHAR(1), @multiplicador INT = 11
	WHILE(@cta < 11)
	BEGIN
		SET @soma = @soma + (SUBSTRING(@cpf, @cta, 1) * @multiplicador)
		SET @cta = @cta + 1
		SET @multiplicador = @multiplicador - 1;
	END
	
	SET @resto = (@soma %11)
	IF(@resto < 2)
	BEGIN
		SET @verificador = '0';
	END
	ELSE
	BEGIN
		SET @verificador = CAST((11 - @resto) AS CHAR(1))
	END

	IF (@verificador = SUBSTRING(@cpf, 11, 1))
	BEGIN
		SET @valido_Sdigito = 1
	END
	ELSE
	BEGIN
		SET @valido_Sdigito = 0
	END

CREATE PROCEDURE sp_criarRa(@ra VARCHAR(10) OUTPUT)
AS
	DECLARE @semestre INT
	DECLARE @ano INT = CAST(SUBSTRING(CONVERT(VARCHAR, YEAR(GETDATE()),120),3,4) AS INT)
	
	DECLARE @rand1 INT = FLOOR(RAND()*10)
	DECLARE @rand2 INT = FLOOR(RAND()*10)
	DECLARE @rand3 INT = FLOOR(RAND()*10)

	DECLARE @verificador INT

	IF(CAST(SUBSTRING(CONVERT(VARCHAR, MONTH(GETDATE()),120),1,2) AS INT) <= 6)
	BEGIN 
		SET @semestre = 1
	END
	ELSE 
	BEGIN
		SET @semestre = 2
	END

	SET @verificador = ((@ano/10)* (@ano%10) * @semestre * @rand1 * @rand2 * @rand3)/4

	IF(@verificador >= 10)
	BEGIN
		SET @verificador = 0
	END
	

	SET @ra = CONCAT('222', CONVERT(VARCHAR, @verificador), CONVERT(VARCHAR, @rand1), CONVERT(VARCHAR, @rand2), CONVERT(VARCHAR, @rand3),
		CONVERT (VARCHAR, @semestre), CONVERT (VARCHAR, @ano))

CREATE PROCEDURE sp_criarEmail(@nome VARCHAR(100), @email VARCHAR(100) OUTPUT)
AS
	DECLARE @first VARCHAR(100) = LEFT(@nome, CHARINDEX(' ', @nome)-1)
	DECLARE @last VARCHAR(100) = REVERSE(LEFT(REVERSE(@nome), CHARINDEX(' ', REVERSE(@nome)) - 1));
	DECLARE @cta INT = 1
	SET @email = CONCAT(@first, '.', @last)
	WHILE EXISTS (SELECT 1 FROM aluno WHERE Email = @email)
	BEGIN
		SET @email =  CONCAT(@first, '.', @last, @cta);
		SET @cta = @cta +1
	END

CREATE PROCEDURE sp_validarSenha @senha VARCHAR(100), @senhaValido BIT OUTPUT
AS
	
	SET @senhaValido = 1
	IF LEN(@senha) < 8
	BEGIN
		SET @senhaValido = 0
	END

	IF @senha NOT LIKE '%[0-9]%'
	BEGIN
		SET @senhaValido = 0
	END



CREATE PROCEDURE sp_atualizarAluno (@cpf CHAR(11), @Senha VARCHAR(100), @saida VARCHAR(100) OUTPUT)
AS
BEGIN
   
    IF EXISTS (SELECT 1 FROM aluno WHERE cpf = @cpf)
    BEGIN
       
        UPDATE aluno
        SET senha = @Senha
        WHERE cpf = @cpf;
        
        SET @saida = 'Senha atualizada com sucesso.'
    END
    ELSE
    BEGIN
        SET @saida = 'aluno não encontrado.'
    END
END

CREATE PROCEDURE sp_inserirExemplar @codigo VARCHAR(13), @nome VARCHAR(150), @qtdPaginas INT, @saida VARCHAR(100) OUTPUT
AS
	
	IF(LEN(@codigo) = 13)
	BEGIN
		INSERT INTO exemplar VALUES (@codigo, @nome, @qtdPaginas)
		INSERT INTO livro VALUES (@codigo)
		SET @saida = 'livro cadastrado'
	END
	ELSE IF(LEN(@codigo) = 8)
	BEGIN
		INSERT INTO exemplar VALUES (@codigo, @nome, @qtdPaginas)
		INSERT INTO revista VALUES (@codigo)
		SET @saida = 'revista cadastrado'
	END
	ELSE
	BEGIN
		SET @saida = 'ISBN OU ISSN Invalido'
	END

CREATE PROCEDURE sp_atualizarNomeExemplar @codigo VARCHAR(13), @nome VARCHAR(150)
AS
	UPDATE exemplar SET nome = @nome
	WHERE codigo = @codigo

CREATE PROCEDURE sp_atualizarQtdPaginasExemplar @codigo VARCHAR(13), @qtdPaginas INT
AS
	UPDATE exemplar SET qtdPaginas = @qtdPaginas
	WHERE codigo = @codigo

CREATE PROCEDURE sp_verificarExemplar @codigo VARCHAR(13)
AS
	SELECT e.codigo, e.nome, e.qtdPaginas FROM exemplar e 
	WHERE e.codigo = @codigo

CREATE PROCEDURE sp_login @email VARCHAR(100), @senha VARCHAR(100), @autorizar BIT OUTPUT
AS
	IF(SELECT Senha FROM aluno WHERE Email = @email) = @senha
	BEGIN
		SET @autorizar = 1
	END
	ELSE
	BEGIN
		SET @autorizar = 0
	END

DECLARE @email VARCHAR(100)
DECLARE @nome VARCHAR(100) = 'Lucas Yoshihiro Shiroma'
EXEC sp_criarEmail @nome, @email OUTPUT
PRINT(@email)

DECLARE @saida VARCHAR(100)
DECLARE @cpf CHAR(11) = '60503978051'
DECLARE @nome  VARCHAR(100) = 'Lucas'
DECLARE @senha VARCHAR(100) = 'senhawe3'
EXEC sp_inserirAluno @cpf, @nome, @senha, @saida OUTPUT
SELECT @saida
SELECT * FROM aluno
SELECT * FROM exemplar

DECLARE @autorizar BIT
DECLARE @email VARCHAR(100)
DECLARE @senha VARCHAR(100)
SET @email = 'Ricardo.Silva'
SET @senha = '11111111'
EXEC sp_login @email, @senha, @autorizar OUTPUT
PRINT(@autorizar)