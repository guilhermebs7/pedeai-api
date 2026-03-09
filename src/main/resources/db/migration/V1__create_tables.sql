CREATE TABLE users(
                      id BIGSERIAL PRIMARY KEY ,
                      nome varchar(100) NOT NULL,
                      email varchar(150) NOT NULL,
                      senha varchar(255) NOT NULL
);
CREATE TABLE roles(
                      id BIGSERIAL PRIMARY KEY ,
                      name varchar(20) NOT NULL
);
CREATE TABLE user_roles(
                           user_id BIGINT NOT NULL ,
                           role_id BIGINT NOT NULL ,

                           CONSTRAINT fk_user_roles_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE ,

                           CONSTRAINT fk_user_roles_role
                               FOREIGN KEY (role_id)
                                   REFERENCES roles(id)
                                   ON DELETE CASCADE ,
                           CONSTRAINT pk_user_roles
                               PRIMARY KEY (user_id,role_id)
);

CREATE TABLE restaurante(
                            id BIGSERIAL PRIMARY KEY ,
                            nome varchar(100) NOT NULL ,
                            descricao TEXT,
                            user_id BIGINT NOT NULL ,
                            CONSTRAINT fk_restaurante_user
                                FOREIGN KEY (user_id)
                                    REFERENCES users(id)
                                    ON DELETE CASCADE

);
CREATE TABLE produto(
                        id BIGSERIAL PRIMARY KEY ,
                        nome varchar(100) NOT NULL,
                        preco NUMERIC(10,2) NOT NULL,
                        restaurante_id BIGINT NOT NULL ,
                        CONSTRAINT fk_produto_restaurante
                            FOREIGN KEY (restaurante_id)
                                REFERENCES restaurante(id)
                                ON DELETE CASCADE
);
CREATE TABLE pedido(
                       id BIGSERIAL PRIMARY KEY ,
                       cliente_id BIGINT NOT NULL ,
                       restaurante_id BIGINT NOT NULL ,
                       status varchar(50) NOT NULL ,
                       valor_total NUMERIC(10,2),
                       data_criacao TIMESTAMP NOT NULL,
                       CONSTRAINT fk_pedido_user
                           FOREIGN KEY (cliente_id)
                               REFERENCES users(id),
                       CONSTRAINT fk_pedido_restaurante
                           FOREIGN KEY (restaurante_id)
                               REFERENCES restaurante(id)


);

CREATE TABLE item_pedido(
                            id BIGSERIAL PRIMARY KEY ,
                            pedido_id BIGINT NOT NULL ,
                            produto_id BIGINT NOT NULL ,
                            quantidade INTEGER NOT NULL ,
                            preco_unitario NUMERIC(10,2) NOT NULL ,
                            CONSTRAINT fk_item_pedido
                                FOREIGN KEY (pedido_id)
                                    REFERENCES pedido(id)
                                    ON DELETE CASCADE ,
                            CONSTRAINT fk_item_produto
                                FOREIGN KEY (produto_id)
                                    REFERENCES produto(id)

);
