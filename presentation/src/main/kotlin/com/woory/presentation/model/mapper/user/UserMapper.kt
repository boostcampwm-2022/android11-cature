package com.woory.presentation.model.mapper.user

import com.woory.data.model.UserModel
import com.woory.presentation.model.User
import com.woory.presentation.model.mapper.UiModelMapper

object UserMapper : UiModelMapper<User, UserModel> {

    override fun asUiModel(domain: UserModel): User =
        User(
            userId = domain.userId,
            data = domain.data.asUiModel()
        )

    override fun asDomain(uiModel: User): UserModel =
        UserModel(
            userId = uiModel.userId,
            data = uiModel.data.asDomain()
        )
}

fun User.asDomain(): UserModel = UserMapper.asDomain(this)

fun UserModel.asUiModel(): User = UserMapper.asUiModel(this)