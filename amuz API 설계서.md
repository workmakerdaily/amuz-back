<h1 style='background-color: rgba(55, 55, 55, 0.4); text-align: center'>AMUZ TO DO API 설계(명세)서</h1>

해당 API 명세서는 'amuz'의 과제 REST API를 명세하고 있습니다.  

- Domain : <http://localhost:4000> 

***
 
<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Auth 모듈</h2>

To Do List 서비스의 인증 및 인가와 관련된 REST API 모듈입니다.
로그인, 회원가입 등의 API가 포함되어 있습니다.  
Auth 모듈은 인증 없이 요청할 수 있습니다. 
  
- url : /api/v1/auth  

***

#### - 로그인  
  
##### 설명

클라이언트는 사용자 아이디와 평문의 비밀번호를 입력하여 요청하고 아이디와 비밀번호가 일치한다면 인증에 사용될 token과 해당 token의 만료 기간을 응답 데이터로 전달 받습니다. 만약 아이디 혹은 비밀번호가 하나라도 틀린다면 로그인 정보 불일치에 해당하는 응답을 받게됩니다. 네트워크 에러, 서버 에러, 데이터베이스 에러, 토큰 생성 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/sign-in**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 사용자의 아이디 | O |
| password | String | 사용자의 비밀번호 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/sign-in" \
 -d "userId=qwer1234" \
 -d "password=qwer1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| accessToken | String | Bearer token 인증 방식에 사용될 JWT | O |
| expiration | Integer | JWT 만료 기간 (초단위) | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "accessToken": "${ACCESS_TOKEN}",
  "expiration": 36000
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 실패 (로그인 정보 불일치)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "SF",
  "message": "Sign in failed."
}
```

**응답 실패 (토큰 생성 실패)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "TCF",
  "message": "Token creation failed."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 아이디 중복 확인  
  
##### 설명

클라이언트는 사용할 아이디를 입력하여 요청하고 중복되지 않는 아이디라면 성공 응답을 받습니다. 만약 아이디가 중복된다면 아이디 중복에 해당하는 응답을 받게됩니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/id-check**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| userId | String | 중복확인 할 사용자의 아이디 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/id-check" \
 -d "userId=qwer1234"
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DI",
  "message": "Duplicated user id."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - 회원가입  
  
##### 설명

클라이언트는 사용자 이름, 사용자 아이디, 비밀번호를 입력하여 요청하고 회원가입이 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 만약 존재하는 아이디일 경우 중복된 아이디에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **POST**  
- end point : **/sign-up**  

##### Request

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| name | String | 사용자의 이름 | O |
| userId | String | 사용자의 아이디 | O |
| password | String | 사용자의 비밀번호 (8~13자의 영문 + 숫자) | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/auth/sign-up"\
 -d "name=홍길동"\
 -d "userId=qwer1234"\
 -d "password=qwer1234"\
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success."
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (중복된 아이디)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DI",
  "message": "Duplicated user id."
}
```

**응답 : 실패 (중복된 전화번호)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "DT",
  "message": "Duplicated user tel number."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>To Do 모듈</h2>

To Do List 서비스의 To Do 관련된 REST API 모듈입니다.  
To Do List 목록 보기, To Do 작성 및 삭제 등의 API가 포함되어 있습니다.  
To Do 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/todo

***

#### - To Do 작성하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Post**
- URL : **/**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| goal | String |  목표 | O |

###### Example

```bash
curl -v -X POST "http://localhost:4000/api/v1/todo" \
 -h "Authorization=Bearer XXXX" \
 -d "goal=다이어트 하기" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - To Do List 조회하기

##### 설명

클라이언트는 요청 후 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/**  

##### Request

###### Header

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/todo" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| toDoList | toDo[] | 목표 목록 | O |
  
**toDoList**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| id | Integer | 아이디 | O |
| userId | String | 사용자의 아이디 | O |
| goal | String | 목표 | O |
| isChecked | Boolean | 완료 여부 | O |
| priority | Integer | 우선순위 | O |
| checkedDate | String | 완료 날짜 | X |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "toDoList": [
    {
      "id": 1,
      "userId": "qwer1234",
      "goal": "다이어트 하기",
      "isChecked": false,
      "priority": 1,
      "checkedDate": null
    }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***

#### - To Do 완료 여부 수정하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/is-check/{id}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| isChecked | Boolean | 완료 여부 | O |

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/todo/is-check/1" \
 -h "Authorization=Bearer XXXX" \
 -d "isChecked=true" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 To Do)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NET",
  "message": "No exist to do."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***
#### - To Do 우선순위 바꾸기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Patch**  
- URL : **/priority**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

###### Request Body
  
| name | type | description | required |
|---|:---:|:---:|:---:|
| is_completed | Boolean | 마감 유무 | O |
| priorityIds | Integer[] | 변경할 우선순위 ID 목록 | O |
}

###### Example

```bash
curl -v -X PATCH "http://localhost:4000/api/v1/todo/priority" \
 -h "Authorization=Bearer XXXX" \
 -h "Content-Type: application/json" \
 -d '{"priorityIds": [1, 3, 2]}'
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 To Do)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NET",
  "message": "No exist to do."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

#### - To Do 삭제하기
  
##### 설명

클라이언트는 요청 헤더에 Bearer 인증 토큰을 포함하여 요청하고 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **Delete**  
- URL : **/{id}**  

##### Request

###### Header

| name | description | required |
|---|:---:|:---:|
| Authorization | Bearer 토큰 인증 헤더 | O |

##### Example

```bash
curl -v -X DELETE "http://localhost:4000/api/v1/todo/1" \
 -h "Authorization=Bearer XXXX" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
  
###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
}
```

**응답 : 실패 (존재하지 않는 사용자)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NI",
  "message": "No exist user id."
}

```

**응답 : 실패 (존재하지 않는 To Do)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NET",
  "message": "No exist to do."
}

```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 : 실패 (권한 없음)**
```bash
HTTP/1.1 403 Forbidden
Content-Type: application/json;charset=UTF-8

{
  "code": "NP",
  "message": "No Permission"
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```

***

<h2 style='background-color: rgba(55, 55, 55, 0.2); text-align: center'>Calendar 모듈</h2>

To Do List 서비스의 Calendar와 관련된 REST API 모듈입니다.  
조회 API가 포함되어 있습니다.  
Calendar 모듈은 모두 인증이 필요합니다.  
  
- url : /api/v1/calendar

#### - 캘린더 조회하기
  
##### 설명

클라이언트는 요청 후 조회가 성공적으로 이루어지면 성공에 대한 응답을 받습니다. 네트워크 에러, 서버 에러, 유효성 실패, 인증 실패, 데이터베이스 에러가 발생할 수 있습니다.  

- method : **GET**  
- URL : **/**  

##### Request

###### Header

###### Example

```bash
curl -X GET "http://localhost:4000/api/v1/calendar" \
```

##### Response

###### Header

| name | description | required |
|---|:---:|:---:|
| Content-Type | 반환되는 Response Body의 Content type (application/json) | O |

###### Response Body

| name | type | description | required |
|---|:---:|:---:|:---:|
| code | String | 결과 코드 | O |
| message | String | 결과 코드에 대한 설명 | O |
| calendar | Calendar[] | 캘린더 데이터 | O |
  
**calendar**  
| name | type | description | required |
|---|:---:|:---:|:---:|
| id | Integer | 아이디 | O |
| userId | String | 사용자의 아이디 | O |
| goal | String | 목표 | O |
| completedDate | Boolean | 완료 날짜 | O |

###### Example

**응답 성공**
```bash
HTTP/1.1 200 OK
Content-Type: application/json;charset=UTF-8

{
  "code": "SU",
  "message": "Success.",
  "toDoList": [
    {
      "id": 1,
      "userId": "qwer1234",
      "goal": "다이어트 하기",
      "completedDate": "2025-03-18"
    }
    ...
  ]
}
```

**응답 실패 (데이터 유효성 검사 실패)**
```bash
HTTP/1.1 400 Bad Request
Content-Type: application/json;charset=UTF-8

{
  "code": "VF",
  "message": "Validation failed."
}
```

**응답 : 실패 (존재하지 않는 데이터)**
```bash
HTTP/1.1 400 BAD_REQUEST
Content-Type: application/json;charset=UTF-8

{
  "code": "NED",
  "message": "No exist data."
}

```

**응답 : 실패 (인증 실패)**
```bash
HTTP/1.1 401 Unauthorized
Content-Type: application/json;charset=UTF-8

{
  "code": "AF",
  "message": "Authentication fail."
}
```

**응답 실패 (데이터베이스 에러)**
```bash
HTTP/1.1 500 Internal Server Error
Content-Type: application/json;charset=UTF-8

{
  "code": "DBE",
  "message": "Database error."
}
```
***