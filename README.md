# API 명세서

| 기능       | Method | URL                       | Request               | Response                                        | 상태 코드            |
|----------| ------ | ------------------------- | --------------------- | ----------------------------------------------- | ---------------- |
| 일정 생성    | POST   | `/schedules`              | title, content, name, password | id, title, content, name, createdAt, modifiedAt | `201 Created`    |
| 전체 일정 조회 | GET    | `/schedules?name={name}`  | Query Parameter: name (선택) | 일정 목록                                           | `200 OK`         |
| 선택 일정 조회 | GET    | `/schedules/{scheduleId}` | Path Parameter: scheduleId | id, title, content, name, createdAt, modifiedAt | `200 OK`         |
| 일정 수정    | PATCH  | `/schedules/{scheduleId}` | title, name, password | id, title, content, name, createdAt, modifiedAt | `200 OK`         |
| 일정 삭제    | DELETE | `/schedules/{scheduleId}` | password              | 없음                                              | `204 No Content` |
