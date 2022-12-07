Create two dynomoDB tables:
bookTable:
bookId
price
quantity
title
Example Data:
100,20,500,"10x rule"

userTable:
userId
name
points

Example Data:
1,Eapen Mani,300


Create SQS:
OrdersQueue

create a topic in SNS:
NotifyCourier

create subscription for this topic :
create email subscription for the store manager.
once the subscription created go and enable the subscription.
