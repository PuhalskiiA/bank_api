create table customer
(
    id        UUID primary key,
    first_name varchar(255) not null,
    last_name  varchar(255) not null,
    role      varchar(255) not null,
    email     varchar(255) not null,
    phone     varchar(255) not null,
    passport  varchar(255) not null,
    birthday  date         not null,
    created   date         not null,
    updated   date         not null,
    status    varchar(255) not null
);

create table account
(
    id          UUID primary key,
    number      varchar(255) not null,
    release     date         not null,
    customer_id UUID         not null,
    created   date         not null,
    updated   date         not null,
    status    varchar(255) not null,

    constraint fk_user_id foreign key (customer_id) references customer (id)
);

create table card
(
    id         UUID primary key,
    number     varchar(255) not null,
    cvv        varchar(255) not null,
    pin        varchar(255) not null,
    release    date         not null,
    expired    date         not null,
    card_status varchar(255) not null,
    balance    numeric(38,2)      not null,
    account_id UUID         not null,
    created   date         not null,
    updated   date         not null,
    status    varchar(255) not null,

    constraint fk_account_id foreign key (account_id) references account (id)
);