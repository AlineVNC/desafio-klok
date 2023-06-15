FROM rabbitmq:3.10-management-alpine

ADD ./rabbitmq.conf /etc/rabbitmq/rabbitmq.conf

RUN rabbitmq-plugins enable --offline rabbitmq_shovel rabbitmq_shovel_management

RUN apk add curl