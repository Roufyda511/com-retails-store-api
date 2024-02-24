
# Retail store

This project implementing some retail store functionality 
- Generating user bill
My Assumption 
- Each Purchaser should be registered before in the store and it's data store in stour DB
- Purchaser has type [Employee, Customer, Affertail]
- Based on Purchaser type, he get discount
- Bill has many items all items has type and unique code
- All Items has Discount except grocery
- Each 100 $ in bill has discount 5$
- You should be logged in to be able to access bill

# Technologies
- Spring Framework
- java 17
- Spring security
- spring JPA
- in memory DB [H2]

 






## API Reference

#### Get Bill

```http
  GET /api/bill/${bill_Id}
```

| Parameter    | Type     | Description                      |
| :----------- | :------- | :---------------------------     |
| Authorization | `string` | **Required**. for authentication |
| Bill_Id      |`number`  | **Required**  bill number

####  login

```http
  GET /api/login/${user_id}
```

| Parameter    | Type     | Description                         |
| :--------    | :------- | :--------------------------------   |
| Authorization | `string` | **Required**. for authentication    |
| Bill_Id      |`number`  | **Required**  user number           |




## API Full Description

 - Also there are more details about APIs response in Swagger documentation
 - I have attached retail_store_swagger_documentation.json file
 - you can upload it on https://editor.swagger.io/ to check all APIs response
 - you can run Application and Get APIs Description using this link
    http://localhost:8080/api/swagger-ui/index.html#/bill-controller/getPurchaserBill

## How to Install and Run the Project
- clone Project ti your machine
- you should have JDK 17 installed or use intellij it detects java version from pom and install it.
- If you are going to use STS install lombok.jar 
- you can clean code and run from Ide
- Also You can run clean code from CMd , in this case you should have maven installed
    - commands 
        - clean package & run unit test
            - mvn clean package -B -X -Dmaven.test.skip=false
            - mvnW clean package -B -X -Dmaven.test.skip=false
        - Clean Code & generate Report
           -  mvn clean test jacoco:report
            - mvnw clean test jacoco:report
        - Generate jacoco report only
            - mvn  jacoco:report
            - mvnw  jacoco:report
                - report will be located in target
            [App_path]/com-retails-store-api/target/         coverage-reports/jacoco/index.html
        - Run Sonar qube analysis 
            -  first use this link to launch sonar qube server locally 
                - https://docs.sonarsource.com/sonarqube/latest/try-out-sonarqube/
            - mvn clean verify sonar:sonar -Dsonar.projectKey=com-retails-store-api -Dsonar.projectName='com-retails-store-api' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=[generate_token]
            - mvnw clean verify sonar:sonar -Dsonar.projectKey=com-retails-store-api -Dsonar.projectName='com-retails-store-api' -Dsonar.host.url=http://localhost:9000 -Dsonar.token=[generate_token]
        - Run & Start Application
            - mvn spring-boot:run
            - mvnw spring-boot:run
        
## How To Test
- Application should be up and running 
- Using Postman to call APIs
- First you need to call login to be authenticated user
- APIs end point
    - localhost:8080/api
- When you logged in a token will be generated , this token will be passed in header of other APIs, to got authenticated.
- To get Purchaser bill , call get bill API, Also there is some date for testing purpose lunched on in memory DB , to facilitate testing process
## Testing Data
| Bill_Id | Purchaser_Type | Discount                   |
| :-------| :------- | :---------------------------     |
| 1       | `CUSTOMER` | 0%  + 5$ per 100 $, less than 2 years           |
| 2       |`EMPLOYEE`  | 30% + 5$ per 100 $
| 3       |`AFFILIATE` |10% + 5$ per 100 $
| 4       |`CUSTOMER`  | 5%  + 5$ per 100 $, more than 2 years
| 5       |`CUSTOMER`  | 0 , bill amount less than 100










