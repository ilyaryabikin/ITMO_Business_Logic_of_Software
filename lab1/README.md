# Задание

Описать бизнес-процесс в соответствии с нотацией BPMN 2.0, после чего реализовать его в виде приложения на базе Spring
Boot.

# Вариант

Использовать бизнес-процесс на основе сценариев использования [Drom.ru](https://www.drom.ru/)

# Порядок выполнения работы

- Выбрать один из бизнес-процессов, реализуемых сайтом из варианта задания.
- Утвердить выбранный бизнес-процесс у преподавателя.
- Специфицировать модель реализуемого бизнес-процесса в соответствии с требованиями BPMN 2.0.
- Разработать приложение на базе Spring Boot, реализующее описанный на предыдущем шаге бизнес-процесс. Приложение должно
  использовать СУБД - PostgreSQL для хранения данных, для всех публичных интерфейсов должны быть разработаны REST API.
- Разработать набор curl-скриптов, либо набор запросов для REST клиента Insomnia или Postman для тестирования публичных
  интерфейсов разработанного программного модуля. Запросы Insomnia или Postman оформить в виде файла экспорта.
- Развернуть разработанное приложение ~~на сервере helios~~ на любом сервере с общедоступным URL для тестирования
  запросов.

# Содержание отчёта

1. Текст задания.
2. Модель потока управления для автоматизируемого бизнес-процесса.
3. UML-диаграммы классов и пакетов разработанного приложения.
4. Спецификация REST API для всех публичных интерфейсов разработанного приложения.
5. Исходный код системы или ссылка на репозиторий с исходным кодом.
6. Выводы по работе.

# Вопросы к защите лабораторной работы

1. Понятие бизнес-логики в программных системах. Уровень бизнес-логики в многоуровневой архитектуре программных систем.
2. Основные концепции, используемые при разработке бизнес-логики. CDI, IoC, управление транзакциями, безопасность,
   распределённая обработка данных.
3. Моделирование бизнес-процессов. BPM и BPMN.
4. Спецификация BPMN 2.0. Принципы составления и основные элементы моделей бизнес-процессов.
5. Объекты потока управления, роли и артефакты в BPMN.
6. Использование Spring Framework для реализации бизнес-логики. Реализация CDI и IoC. Связь уровня бизнес-логики с
   другими уровнями архитектуры программных систем в Spring.
7. Spring Boot. Способы конфигурации бинов. Двухфазовый, трёхфазовый конструктор.
8. Профили запуска приложения в Spring Boot.
9. Реализация REST API с помощью Spring MVC REST.
