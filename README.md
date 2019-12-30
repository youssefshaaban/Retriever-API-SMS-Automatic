# Retriever-API-SMS-Automatic
SMS Retriever API, you can perform SMS-based user verification in your Android app automatically, without requiring the user to manually type verification codes, and without requiring any extra app permissions. When you implement automatic SMS verification in your app
the verification flow looks like this:

<img src="./sms_retriever_api.png" width=“400”/>

## Prerequisites
The SMS Retriever API is available only on Android devices with Play services version 10.2 and newer.

## Important
The standard SMS format is given blow.

    <#> Your ExampleApp code is: 123ABC78 
    fGXfpK7Ticp

SMS alwayse starts with <#> sign and have a hash key fGXfpK7Ticp to identify your app it is generated with your app's package id. You just need to get this has key from app and share with your server. 
In next few steps you will see how to create hash keys.
## Important
hashKey in debugMode is different releaseMode 
when get hashkey for release share with your server 

## Documentation 
[(`see more details Google Documentation`]( https://developers.google.com/identity/sms-retriever/overview))

