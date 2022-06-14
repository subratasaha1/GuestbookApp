"# GuestbookApp" 

guest username is "john" and password is "fun123"
admin username is "mary" and password is "fun123"

#to generate the token for authentication

/guestbookapp/auth/login


#list of users
/guestbookapp/api/administrator/users

#specific user
/guestbookapp/api/administrator/users/{userId}


#all the guest entries for a specific user
/guestbookapp/api/administrator/users/guestentries/{userId}

#a specific guest entry for guest entry id
/guestbookapp/api/administrator/guestentries/{guestentryId}

#delete a specific guest entry by guest entry id
/guestbookapp/api/administrator/guestentries/{guestentryId}

#update the approve the flag patch mapping
/guestbookapp/api/administrator/guestentries/{guestentryId}

{
    "isApproved":true
}


#update the guest entry by guest entry id
/guestbookapp/api/administrator/guestentries/{guestentryId}

{
    "id": 75,
    "comment": "I am the image. Approve me.",
    "photos": "http://localhost:8073/downloadFile/1_thumbnail.jpg",
    "user": {
        "id": 1
    },
    "approved": true
}

#create guest entry
/guestbookapp/api/guestusers/v1/guestentry/{userId}
