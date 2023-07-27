# Promotion Management Backend

Provide promotion management services.

Using Tomcat HTTP to communicate

## Database

Database Name: promotion

### Tables

---

#### promotion.promotion

All the promotion with their status and properties.

Columns:

- `pid` Promotion ID
- `name` Name of the promotion
- `business` Business that the promotion belongs to
- `category` Category of the promotion, `DAILY=0` for daily promotion, `LARGE=1` for large promotion
- `uid` Creator's UID
- `start` Date when promotion starts
- `end` Date when promotion ends
- `primary_approver` Primary approver's UID
- `secondary_approver` Secondary approver's UID

---

#### promotion.coupon

Coupon from all promotions

Columns:
- `cid` Coupon ID
- `name` Name of the coupon
- `promotion` Promotion that the coupon belongs to
- `used` Whether the coupon is used
- `value` Value of the coupon
- `start` Date when the coupon become available
- `end` Date when the coupon become unavailable

---

#### promotion.user

Users that may interact with promotion management, this table is used for authentication

Columns:

- `uid` User ID
- `name` User name
- `auth` Authentication level
- `pwd` User's password

---

## Usage

### Post a promotion

1. Submit necessary information about the promotion to post  [`/promotion/create`](#promotion_create)
2. Waiting for primary approval [`/promotion/approve/primary`](#approve_primary)
3. Waiting for secondary approval [`/promotion/approve/secondary`](#approve_secondary)
4. When the approval complete, you can [post coupons](#coupon_add) for your promotion if the promotion begins

## Method List

### Search

---

#### promotion/search/id

Search a specific promotion via promotion's id, return null if not found

Require authentication level: `ANY`

Arguments:
- `Integer id` id of the promotion

Returns:

`Promotion promotion` promotion with input ID, `null` if not found

Example:

> Input: `http://SERVER/promotion/search/id?id="0"`
>
> Returns: ```{"pid": "0", "name": "testA", "business": "test", "category": 0,
> "creator": "test_staff", "primary_approver": "test_primary", "secondary_approver": "test_secondary",
> "start": "2022-1-1", "end": "2023-1-1"}```

---

#### promotion/search/vague

Search a list of promotions via their details, return a list

Require authentication level: `ANY`

Arguments:
- `String name` name of the promotions, support vague search(following SQL string format)
- `Integer category` category of the promotion, `DAILY=0`, `LARGE=1`
- `String creator` creator of the promotion, support vague search(following SQL string format)
- `Date start` time duration begin-point, filter promotions ends before this point of time
- `Date end` time duration end-point, filter promotions start after this point of time

Returns:

`List<Promotion> promotions` promotions that matches input conditions

Example:

> Input: `http://SERVER/promotion/search/vague?name=%25test%25&category=0&creator=%25&start=1970-1-1&end=2100-1-1`
> 
> Returns: ```[{"pid": 0, "name": "testA", "business": "test", "category": 0,
> "creator": "test_staff", "primary_approver": "test_primary", "secondary_approver": "test_secondary",
> "start": "2022-1-1", "end": "2023-1-1"}]```

---

### Promotion management

---

#### /promotion/create <span id="promotion_create"><span/>

Create a promotion with necessary information

Require authentication level: `STAFF`

Arguments:
- `String token` token of current session, used for authentication
- `String creator` creator's name of the promotion
- `String name` name of the promotion
- `Integer category` category of the promotion, `DAILY=0`, `LARGE=1`
- `String business` business where the promotion belongs to
- `Date start` when the promotion starts
- `Date end` when the promotion ends

Returns:

`Boolean result` Return `true` if successfully created

Example:

> Input: `http://SERVER/promotion/create?token=testToken&creator=test_staff&name=testB&category=0&business=test&start=2023-8-1&end=2023-11-11`
> 
> Returns: `true`

---

#### /promotion/approve/primary <span id="approve_primary"><span/>

Primary approval of a promotion

Require authentication level: `PRIMARY_STAFF`

Arguments:
- `String token` token of current session, used for authentication
- `String promotionId` ID of the promotion

Returns:

`Boolean result` Return `true` if successfully approved

Example:

> Input: `http://SERVER/promotion/approve/primary?token=testToken&id=0`
>
> Returns: `true`

---

#### /promotion/approve/secondary <span id="approve_secondary"><span/>

Secondary approval of a promotion

Require authentication level: `SECONDARY_STAFF`

Arguments:
- `String token` token of current session, used for authentication
- `String promotionId` id of the promotion

Returns:

`Boolean result` Return `true` if successfully approved

Example:

> Input: `http://SERVER/promotion/approve/secondary?token=testToken&id=0`
>
> Returns: `true`

---

#### /coupon/add <span id="coupon_add"><span/>

Add coupon(s) to a promotion

Require authentication level: `STAFF`

Arguments:
- `String token` token of current session, used for authentication
- `String name` name of the coupon
- `Double value` value of the coupon
- `String promotionId` ID of a promotion the coupons belong to
- `Date start` start-point of the available duration
- `Date end` end-point of the available duration
- `Integer count` count of the coupons

Returns:

`Boolean result` Return `true` if successfully added

Example:

> Input: `http://SERVER/coupon/add?token=testToken&name=test&value=10.0&promotionId=testA&start=2023-11-1&end=2023-11-11&count=100`
>
> Returns: `true`

### Other

---

#### /coupon/get <span id="coupon_get"><span/>

Get coupon(s) from a promotion

Require authentication level: `COMMON`

Arguments:
- `String promotionId` ID of a promotion the coupons belong to
- `String name` name of the coupon
- `Integer count` count of the coupons

Returns:

`List<Coupon> coupons` a list of coupons, return null indicates required count cannot be satisfied or bad arguments

Example:

> Input: `http://SERVER/coupon/get?promotionId=testA&name=test&count=2`
> 
> Returns: `[{"cid":"0","name":"test","promotion":"testA","value":10.0,"start":"2023-11-1","end":"2023-11-11","used":false},{"cid":"1","name":"test","promotion":"testA","value":10.0,"start":"2023-11-1","end":"2023-11-11","used":false}]`

---

#### /user/login <span id="login"><span/>

User login, used for authentication

Require authentication level: `COMMON`

Arguments:
- `uid` user ID
- `pwd` password

Returns:

`String token` session token for authenticated communication, return null if bad arguments

Example:

> Input: `http://SERVER/user/login?uid=test_staff&pwd=password`
> 
> Returns: `TEST_TOKEN`

---