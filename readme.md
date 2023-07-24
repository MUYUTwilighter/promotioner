# Promotion Management Backend

Provide promotion management services.

Using Tomcat HTTP to communicate

## Usage

### Post a promotion

1. Submit necessary information about the promotion to post  [`/promotion/create`](#promotion_create)
2. Waiting for primary approval [`/promotion/approve/primary`](#approve_primary)
3. Waiting for secondary approval [`/promotion/approve/secondary`](#approve_secondary)
4. When the approval complete, you can [post coupons](#coupon_add) for your promotion if the promotion begins

## Method List

### Search

#### `/search/id`

Search a specific promotion via promotion's id, return null if not found

Arguments:
- `Integer id` id of the promotion

#### `/search/vague`
Search a list of promotions via their details, return a list

Arguments:
- `String name` name of the promotions, support vague search(following SQL string format)
- `Integer category` category of the promotion, `DAILY=0`, `LARGE=1`
- `String creator` creator of the promotion, support vague search(following SQL string format)
- `Date start` time duration begin-point, filter promotions ends before this point of time
- `Date end` time duration end-point, filter promotions start after this point of time

### Promotion management

#### `/promotion/create` <span id="promotion_create"><span/>

Create a promotion with necessary information

Arguments:
- `String creator` creator's name of the promotion
- `String name` name of the promotion
- `Integer category` category of the promotion, `DAILY=0`, `LARGE=1`
- `String business` business where the promotion belongs to
- `Date start` when the promotion starts
- `Date end` when the promotion ends

#### `/promotion/approve/primary` <span id="approve_primary"><span/>
Primary approval of a promotion

Arguments:
- `Integer id` id of the promotion
- `Integer approver` approver's id

#### `/promotion/approve/secondary` <span id="approve_secondary"><span/>
Secondary approval of a promotion

Arguments:
- `Integer id` id of the promotion
- `Integer approver` approver's id

#### `/coupon/add` <span id="coupon_add"><span/>
Add coupon(s) to a promotion

Arguments:
- `String name` name of the coupon
- `Double value` value of the coupon
- `Integer promotion` id of a promotion the coupons belong to
- `Date start` start-point of the available duration
- `Date end` end-point of the available duration
- `Integer count` count of the coupons

### For public

#### `/coupon/use`

Use coupon

Arguments:

- `Long id` id of the coupon to be used

