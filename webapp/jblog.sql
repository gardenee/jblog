-- 계정 생성
CREATE USER jblog IDENTIFIED BY jblog;
GRANT resource, connect TO jblog;


-- users 테이블
CREATE TABLE users (
    user_no NUMBER
    ,id VARCHAR2(50) NOT NULL UNIQUE
    ,user_name VARCHAR2(100) NOT NULL
    ,password VARCHAR2(50) NOT NULL
    ,join_date DATE NOT NULL
    ,PRIMARY KEY(user_no)
);

DROP TABLE users;

CREATE SEQUENCE seq_users_no
START WITH 1
INCREMENT BY 1
NOCACHE;

DROP SEQUENCE seq_users_no;


-- blog 테이블
CREATE TABLE blog (
    id VARCHAR2(50)
    ,blog_title VARCHAR2(200) NOT NULL
    ,logo_file VARCHAR2(200)
    ,PRIMARY KEY(id)
    ,CONSTRAINT blog_fk FOREIGN KEY (id)
    REFERENCES users(id)
);

DROP TABLE blog;


-- category 테이블
CREATE TABLE category (
    cate_no NUMBER
    ,id VARCHAR2(50)
    ,cate_name VARCHAR2(200) NOT NULL
    ,description VARCHAR2(500)
    ,reg_date DATE NOT NULL
    ,PRIMARY KEY (cate_no)
    ,CONSTRAINT category_fk FOREIGN KEY (id)
    REFERENCES blog(id)
);

DROP TABLE category;

CREATE SEQUENCE seq_category_no
START WITH 1
INCREMENT BY 1
NOCACHE;

DROP SEQUENCE seq_category_no;


-- post 테이블
CREATE TABLE post(
    post_no NUMBER
    ,cate_no NUMBER
    ,post_title VARCHAR2(300) NOT NULL
    ,post_content VARCHAR2(4000)
    ,reg_date DATE NOT NULL
    ,PRIMARY KEY (post_no)
    ,CONSTRAINT post_fk FOREIGN KEY(cate_no)
    REFERENCES category(cate_no)
);

DROP TABLE post;

CREATE SEQUENCE seq_post_no
START WITH 1
INCREMENT BY 1
NOCACHE;

DROP SEQUENCE seq_post_no;


-- comments 테이블
CREATE TABLE comments(
    cmt_no NUMBER
    ,post_no NUMBER
    ,user_no NUMBER
    ,cmt_content VARCHAR2(1000) NOT NULL
    ,reg_date DATE NOT NULL
    ,PRIMARY KEY (cmt_no)
    ,CONSTRAINT comments_fk1 FOREIGN KEY (post_no)
    REFERENCES post(post_no)
    ,CONSTRAINT comments_fk2 FOREIGN KEY (user_no)
    REFERENCES users(user_no)
);

DROP TABLE comments;

CREATE SEQUENCE seq_comments_no
START WITH 1
INCREMENT BY 1
NOCACHE;

DROP SEQUENCE seq_comments_no;



COMMIT;

ROLLBACK;

SELECT *
FROM users;

SELECT *
FROM blog;

SELECT *
FROM category;

SELECT *
FROM post;

SELECT *
FROM comments;