Курсовой проект - Банковские карты

Автоматическая рассылка уведомление клиентам банка о завершении срока действий их банковских карт
Есть объекты:
	Клиент (ФИО, Дата рождения, …)
	Банковская карта (Клиент, Номер карты, Дата выдачи, Дата окончания действия, …)

Разработать задание по расписанию, которое будет осуществлять рассылку уведомления клиентам при 
завершении срока действия банковской карты. Дополнительно предусмотреть следующие функции: 
1.	Создание клиента
Если клиент уже был раннее заведен, возвращать его идентификатор, чтобы избежать дублей 
2.	Создание карты
Заводит новую карту клиенту (опционально, можно проверять уникальность номера карты при заведении)
3.	Аннулирование карты
Закрывает действующую карту клиента
  
Использовать build tool (maven, gradle), vcs, unit тесты
 
Опционально, можно усложнить
 
1.	Абстракция над хранением, чтобы можно было менять реализацию хранения клиентских данных, например, с HashMap, на БД
2.	Ходить в БД через JdbcTemplate, spring-data, hibernate, …
3.	Выделить сервис в отдельный артефакт (maven, gradle), класс Main для демонстрации должен лежать в другом артефакте
4.	Сделать spring-boot с rest api
5.	Разработать UI для заведения новых клиентов и карт в runtime 
6.	Запустить образ приложения в докере.