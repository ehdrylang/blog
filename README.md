# blog

# API 명세

## jar 다운로드 링크 : https://github.com/ehdrylang/blog/raw/main/blog-1.0.jar

## 1. 블로그 검색

```jsx
GET /api/search/blog HTTP/1.1
```

### Request - Parameter

| Name | Type | Description | Required |
| --- | --- | --- | --- |
| query | String | 검색 질의어 (키워드) | O |
| sort | String | 결과 정렬 방식, ACCURACY(정확도순) 또는 RECENCY(최신순), 기본 값 ACCURACY | X |
| page | Integer | 결과 페이지 번호, 1~50 사이의 값, 기본 값 1 | X |
| size | Integer | 한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10 | X |

### Sample

```jsx
/api/search/blog?keyword=정동교&sort=RECENCY&page=3&size=5
```

### Response

| Name | Type | Description |
| --- | --- | --- |
| totalCount | Integer | 검색된 문서 수 |
| pageableCount | Integer | totalCount 중 노출 가능 문서 수 |
| items | array | 블로그 객체 배열 |

**Items**

| Name | Type | Description |
| --- | --- | --- |
| title | String | 블로그 글 제목 |
| contents | String | 블로그 글 요약 |
| url | String | 블로그 글 URL |
| blogName | String | 블로그의 이름 |
| thumbnail | String | 검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음 |
| datetime | Datetime | 블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss] |

### Sample

```json
{
   "totalCount":21355172,
   "pageableCount":800,
   "items":[
      {
         "title":"고대가요(구지가, 공무도하가, 황조가, 해가사)에 표현된 ‘다른 세계’",
         "contents":"여기서 노인은 현자(賢者)를 의미한다. 수로부인은 손가락으로 진리(꽃)를 가리켰고, 노인은 목숨을 걸고 벼랑으로 올라가 꽃을 꺾는 행위를 실천한 것이다. • <b>바다</b>를 지배하는 용왕도 수로부인의 명성을 익히 들었는가 보다. 부임지로 향하는 남편(순정공) 앞에서 수로부인을 납치한 것을 보면 말이다. 마을 노인의 지혜...",
         "url":"https://blog.naver.com/olpheus/222879915941",
         "blogName":"방아 찧는 토끼",
         "thumbnail":"https://search4.kakaocdn.net/argon/130x130_85_c/KuduIGY4vbz",
         "datetime":"2022-09-20T21:38:00"
      },
      ...
   ]
}
```

## 2. 인기 검색어 목록

사용자들이 많이 검색한 순서대로, 최대 10개의 검색 키워드를 검색 횟수와 함께 목록으로 제공하는 API

```jsx
GET /api/search/blog/keyword HTTP/1.1
```

### Request - Parameter

X

### Sample

```jsx
/api/search/blog/keyword
```

### Response

| Name | Type | Description |
| --- | --- | --- |
| keywords | array | keyword 객체 배열 |

**keyword**

| Name | Type | Description |
| --- | --- | --- |
| keyword | String | 검색어 키워드 |
| count | Integer | 검색 횟수 |

### Sample

```json
{
   "keywords":[
      {
         "keyword":"바다",
         "count":13
      },
      {
         "keyword":"정동교",
         "count":2
      },
      ...
   ]
}
```

# 문제 해결 전략 및 생각해본 것
- 추후 카카오 API 이외에 새로운 검색 소스가 추가될 수 있음을 고려하기
   + `DIP`를 적용하여 BlogSearcher 라는 인터페이스를 만들고 이후 새로운 검색 소스가 해당 인터페이스를 구현하여 변경에 유연하도록 처리
   + BlogSearcher 인터페이스에 isAvailable() 메서드를 두어 현재 검색 소스가 사용가능한 상태인지 알 수 있도록 하는 메서드를 두어 장애 발생 시 자연스럽게 다른 소스로 유도하도록 설계
      - 서킷브레이커 같은 것이 사용되면 이용될 수 있을 것으로 예상
- 오픈 소스 `Redis` 사용
   + 애플리케이션 스케일 아웃 되었을 때 `동시성` 처리에 필요하다고 판단하여 사용
   + 블로그 키워드 검색이 이뤄지면, `sorted set`에 해당 키워드를 key로 갖도록 하여 score를 +1 해주면서 검색 횟수를 저장하고 인기검색어 랭킹을 구현할 때는 `ZREVRANGE` 로 많이 검색된(=score가 높은) 10개를 추출하도록 처리
- 트래픽이 많은 경우를 대비하여 `캐시` 고려하기
   + 외부 API(블로그 조회)의 경우 비용이 많이 들기도 하고 최신순이 아니라면 데이터가 빈번히 바뀌는 것도 아니기 때문에 적당한 만료시간을 갖도록하여 캐시할 수 있으면 도움됨
- redis에 장애가 발생하는 것은 고려하다가 오버엔지니어링이라고 판단
   + redis에 장애가 나서 검색 횟수를 알 수 없게된 경우 다시 복구했을 때를 처리방안 고민
      - 매 검색 시, 이력을 DB에 남기도록 처리하고 매일 배치를 통해서 전날 까지의 누적에 합산
      - 전날 까지의 누적 + 오늘 쌓인 이력으로 redis sorted set에 스코어로 다시 입력 가능하도록 처리해야하나 과제 범위를 넘어선다고 판단  
