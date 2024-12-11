participant Clien
participant Gateway
participant Authentication service
participant Account service
participant Library service
participant Order service

    Clien->>+Gateway: POST /account-service/api/v1/account
    Note over Clien,Gateway: Регистрация пользователя
    Gateway->>+Authentication service: 
    Authentication service-->>-Gateway: token verification
    Gateway->>+Account service: POST /account-service/api/v1/account
    Account service-->>-Gateway: 201 Created
    Gateway-->>-Clien: 201 Created

    Clien->>+Gateway: POST /library-serviceapi/api/v1/book/order
    Note over Clien,Gateway: Запрос на пополнение библиотеки
    Gateway->>+Authentication service: 
    Authentication service-->>-Gateway: token verification
    Gateway->>+Library service: POST /library-serviceapi/api/v1/book/order
    Library service-->>+Order service: POST /order-service/api/v1/order
    Order service-->>-Library service: 201 Created
    Library service-->>-Gateway: 201 Created
    Gateway-->>-Clien: 201 Created

    Clien->>+Gateway: POST /library-serviceapi/api/v1/book/{bookId}/take
    Note over Clien,Gateway: Запрос на колучение книги
    Gateway->>+Authentication service: 
    Authentication service-->>-Gateway: token verification
    Gateway->>+Library service: POST /library-serviceapi/api/v1/book/{bookId}/take
    Library service-->>+Account service: POST /order-service/api/v1/order
    Account service-->>-Library service: 201 Created
    Library service-->>-Gateway: 201 Created
    Gateway-->>-Clien: 201 Created