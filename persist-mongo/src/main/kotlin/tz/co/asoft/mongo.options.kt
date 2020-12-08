package tz.co.asoft

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI

fun MongoOptions.toClient() = MongoClient(MongoClientURI("mongodb://$user:$password@$host:$port"))