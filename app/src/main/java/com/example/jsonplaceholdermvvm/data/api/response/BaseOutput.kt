package com.example.jsonplaceholdermvvm.data.api.response

class BaseOutput<T> {
    var Response: ArrayList<APIResponse>? = null
    var ResultSet: T? = null
}