package com.example.userloginapp



class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }
    suspend fun getUserById(userId: Int): User? {
        return userDao.getUserById(userId)
    }
    suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}
