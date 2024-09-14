package com.kopring.repository

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.extensions.spring.SpringExtension
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardImageRepositoryTest @Autowired constructor(
    private val boardImageRepository: BoardImageRepository,
    private val boardRepository: BoardRepository,
    private val entityManager: EntityManager
) : BehaviorSpec({
    extensions(SpringExtension)

    beforeEach {
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0")
        entityManager.createNativeQuery("TRUNCATE TABLE board_image")
        entityManager.createNativeQuery("TRUNCATE TABLE board")
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=1")
    }
})