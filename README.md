# blog

# API 명세

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
      \...
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

# 문제 해결 전략
