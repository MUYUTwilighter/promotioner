# Promotion Management Backend

Provide promotion management services.

Using Tomcat HTTP to communicate

## Database

Database Name: promotion

### Tables

#### promotion.promotion

All the promotion with their status and properties.

Columns:

- `pid` Promotion ID
- `promotion_name` Name of the promotion
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
- `coupon_name` Name of the coupon
- `promotion` Promotion that the coupon belongs to
- `used` Whether the coupon is used
- `value` Value of the coupon
- `start` Date when the coupon become available
- `end` Date when the coupon become unavailable

---

#### promotion.user <span id = "table_user"></span>

Users that may interact with promotion management, this table is used for [authentication](#auth)

Columns:

- `uid` User ID
- `user_name` User name
- `auth` Authentication level, see [User & authentication for detail](#auth)
- `pwd` User's password

---

## Usage

### Post a promotion

To post a promotion, you need 

1. Submit necessary information about the promotion to post  [`/promotion/create`](#promotion_create)
2. Waiting for primary approval [`/promotion/approve/primary`](#approve_primary)
3. Waiting for secondary approval [`/promotion/approve/secondary`](#approve_secondary)
4. When the approval complete, you can [post coupons](#coupon_add) for your promotion if the promotion begins

### User & authentication <span id = "auth"><span/>

Some of the methods require specific authentication level to use,
here tells details about authentication procedures used by this project.

Authentication level is a integer value in data-table [promotion.user.auth](#table_user),
which means any roles are stored in one integer, and is read as bit to identify a user's role

The integer's bit sequence is following rules:

- `user.auth & 0b00000001 != 0`: `STAFF`
- `user.auth & 0b00000010 != 0`: `PRIMARY_STAFF`
- `user.auth & 0b00000100 != 0`: `SECONDARY_STAFF`
- `user.auth & 0b10000000 != 0`: `DATABASE_ADMIN`
- `user.auth & 0x0FFFFFFF != 0`: `ROOT`

For example, to register a user with authority `PRIMARY_STAFF` and `STAFF`,
you can use method [/user/register](#user_register) with argument auth set to `PRIMARY_STAFF | STAFF`

## Method List

### Search

#### promotion/search/id

Search a specific promotion via promotion's id, return null if not found

Type: `HTTP GET`

Require authentication level: `ANY`

Arguments:

- `Long id` id of the promotion

Returns:

`Promotion promotion` promotion with input ID, `null` if not found

Example:

> Input: `http://SERVER/promotion/search/id?id="0"`
>
> Returns: ```{"pid":0,"promotionName":"testA","category":0,"business":"test","uid":0,"startDate":"2022-1-1","endDate":"2023-1-1","primaryApprover":0,"secondaryApprover":0}```

---

#### promotion/search/vague

Search a list of promotions via their details, return a list

Type: `HTTP GET`

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
> Returns: ```[{"pid":0,"pName":"testA","category":0,"business":"test","uid":0,"startDate":"2022-1-1","endDate":"2023-1-1","primaryApprover":0,"secondaryApprover":0}]```

---

### Promotion management

#### /promotion/create <span id="promotion_create"><span/>

Create a promotion with necessary information

Type: `HTTP POST`

Require authentication level: `STAFF`

Arguments:

- `String token` token of current session, used for authentication
- `String name` name of the promotion
- `Integer category` category of the promotion, `DAILY=0`, `LARGE=1`
- `String business` business where the promotion belongs to
- `Date start` when the promotion starts
- `Date end` when the promotion ends

Returns:

`Boolean result` Return `true` if successfully created

Example:

> Input: `http://SERVER/promotion/create?token=testToken&name=testB&category=0&business=test&start=2023-8-1&end=2023-11-11`
> 
> Returns: `true`

---

#### /promotion/approve/primary <span id="approve_primary"><span/>

Primary approval of a promotion

Type: `HTTP PUT`

Require authentication level: `PRIMARY_STAFF`

Arguments:

- `String token` token of current session, used for authentication
- `Long pid` ID of the promotion

Returns:

`Boolean result` Return `true` if successfully approved

Example:

> Input: `http://SERVER/promotion/approve/primary?token=testToken&pid=0`
>
> Returns: `true`

---

#### /promotion/approve/secondary <span id="approve_secondary"><span/>

Secondary approval of a promotion

Type: `HTTP PUT`

Require authentication level: `SECONDARY_STAFF`

Arguments:

- `String token` token of current session, used for authentication
- `Long pid` ID of the promotion

Returns:

`Boolean result` Return `true` if successfully approved

Example:

> Input: `http://SERVER/promotion/approve/secondary?token=testToken&pid=0`
>
> Returns: `true`

---

#### /coupon/add <span id="coupon_add"><span/>

Add coupon(s) to a promotion

Type: `HTTP POST`

Require authentication level: `STAFF`

Arguments:

- `String token` token of current session, used for authentication
- `String name` name of the coupon
- `Double value` value of the coupon
- `Long pid` ID of a promotion the coupons belong to
- `Date start` start-point of the available duration
- `Date end` end-point of the available duration
- `Integer count` count of the coupons

Returns:

`Integer success` count of coupons that is successfully added

Example:

> Input: `http://SERVER/coupon/add?token=EXAMPLE_TOKEN&name=test&value=10.0&pid=testA&start=2023-11-1&end=2023-11-11&count=100`
>
> Returns: `100`

### Other

#### /coupon/get <span id="coupon_get"><span/>

Get coupon(s) from a promotion

Type: `HTTP GET`

Require authentication level: `ANY`

Arguments:

- `Long pid` ID of a promotion the coupons belong to
- `String name` name of the coupon
- `Integer count` count of the coupons

Returns:

`List<Coupon> coupons` a list of coupons, return null indicates required count cannot be satisfied or bad arguments

Example:

> Input: `http://SERVER/coupon/get?promotionId=testA&name=test&count=2`
> 
> Returns: `[{"cid":"0","cName":"test","pid":0,"value":10.0,"start":"2023-11-1","end":"2023-11-11","used":false},{"cid":"1","cName":"test","pid":0,"value":10.0,"start":"2023-11-1","end":"2023-11-11","used":false}]`

---

#### /user/login <span id="login"><span/>

User login, used for authentication

Type: `HTTP GET`

Require authentication level: `ANY`

Arguments:

- `Long uid` user ID
- `String pwd` password

Returns:

`String token` session token for authenticated communication, return null if bad arguments

Example:

> Input: `http://SERVER/user/login?uid=0&pwd=password`
> 
> Returns: `EXAMPLE_TOKEN`

---

#### /user/query <span id="user_query"><span/>

Query user, with password hidden

Type: `HTTP GET`

Require authentication level: `ANY`

Arguments:

- `Long uid` user ID

Returns:

`User user` null if user not found

Example:
> Input: `http://SERVER/user/query?uid=0`
> 
> Returns: `{"uid":0,"userName":test_staff,"auth":1}`

---

#### /user/register <span id = "user_register"><span/>

Register a user

Type: `HTTP POST`

Require authentication level: `DATABASE_ADMIN` (Higher than the user to register)

Arguments:

- `String token` token of current session, used for authentication
- `String name` name of the user
- `String pwd` password of the user
- `Integer auth` authentication level of the user

Returns:

`Long id` ID of registered user, return 0 if failed

Example:

> Input: `http://SERVER/user/modify/auth?token=EXAMPLE_TOKEN&name=test_common&pwd=password&auth=0`
>
> Returns: `4`

---

#### /user/modify/auth

Modify authentication level of a user

Type: `HTTP PUT`

Require authentication level: `ANY` (Higher than target user)

Arguments:

- `String token` token of current session, used for authentication
- `Long uid` ID of target user
- `Integer auth` authentication level to set

Returns:

`Boolean result` true if authentication successfully modified

Example:

> Input: `http://SERVER/user/modify/auth?token=EXAMPLE_TOKEN&uid=2&auth=2`
> 
> Returns: `true`

---

#### /user/modify/property

Modify properties (except authentication level) of current communicating user

Type: `HTTP PUT`

Require authentication level: `ANY`

Arguments:

- `String token` token of current session, used for authentication
- `Long name` name to set
- `String pwd` password to set

Returns:

`Boolean result` true if properties successfully modified

Example:

> Input: `http://SERVER/user/modify/property?token=EXAMPLE_TOKEN&name=test_secondary&pwd=password`
>
> Returns: `true`

---