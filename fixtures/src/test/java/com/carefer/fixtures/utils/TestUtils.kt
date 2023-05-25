package com.carefer.fixtures.utils

import com.carefer.entities.news.favorite.local.News
import com.carefer.entities.news.favorite.remote.RemoteNews
import com.carefer.fixtures.domain.entity.local.Announcement
import com.carefer.fixtures.domain.entity.local.AnnouncementImportance
import com.carefer.fixtures.domain.entity.local.Attachment
import com.carefer.fixtures.domain.entity.remote.RemoteAnnouncement
import com.carefer.fixtures.domain.entity.remote.RemoteAttachment
import java.util.*

class TestUtils {
    companion object {

        val REMOTE_NEWS_DTO = RemoteNews(
            false,
            null,
            "2020-11-02T09:59:11.407",
            "Test description api",
            null,
            "2020-11-02T09:59:11.407",
            "f0001f01-bfa3-45d5-9c18-affc619da703",
            "TEst API"
        )

        val NEWS_BO = News(
            "f0001f01-bfa3-45d5-9c18-affc619da703",
            "TEst API",
            "02 November 2020",
            "",
            "Test description api",
            "",
            false
        )

        val NEWS_BO2 = News(
            "8bd59b22-945e-480c-89c7-9a90f223f376",
            "TEst API",
            "02 November 2020",
            "http://40.114.214.132:81/api/FileManger/Download/008c7742-804c-4b8f-a635-7b57f092731a?Token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2MDQzNDU4NDQsImlzcyI6IjAwOGM3NzQyLTgwNGMtNGI4Zi1hNjM1LTdiNTdmMDkyNzMxYSJ9.wD1a1VsVmdZqBQ55QiLEOvIxYPSy1q1ZXXgxvApFx7Y",
            "Test description api",
            "http://40.114.214.132:82/api/News/8bd59b22-945e-480c-89c7-9a90f223f376",
            false
        )

        val NEWS_LIST_BO = Collections.unmodifiableList(
            ArrayList<News>().apply {
                add(NEWS_BO)
                add(NEWS_BO2)
            }
        )


        val ATTACHMENT_REMOTE = RemoteAttachment(
            "http://40.114.214.132:82/api/Announcement/ae9077a7-f9b6-4e4f-8f57-2f848979d3bd",
            attachmentName = "test"
        )

        val ATTACHMENT_REMOTE_NULL = RemoteAttachment(
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val ATTACHMENT_BO = Attachment(
            "http://40.114.214.132:82/api/Announcement/ae9077a7-f9b6-4e4f-8f57-2f848979d3bd",
            attachmentName = "test"
        )

        val ATTACHMENT_BO_DEFAULT = Attachment(
            "",
            attachmentName = ""
        )


        val ANNOUNCEMENT_REMOTE = RemoteAnnouncement(
            false,
            "http://40.114.214.132:82/api/Announcement/ae9077a7-f9b6-4e4f-8f57-2f848979d3bd",
            "2020-11-05T11:11:21.937",
            "h",
            "3",
            null,
            "ae9077a7-f9b6-4e4f-8f57-2f848979d3bd",
            "string123",
            null
        )

        val ANNOUNCEMENT_BO = Announcement(
            false,
            "http://40.114.214.132:82/api/Announcement/ae9077a7-f9b6-4e4f-8f57-2f848979d3bd",
            "05 November 2020",
            AnnouncementImportance.URGENT,
            "ae9077a7-f9b6-4e4f-8f57-2f848979d3bd",
            "string123",
            emptyList()
        )


        val ANNOUNCEMENT_BO2 = Announcement(
            false,
            "http://40.114.214.132:82/api/Announcement/beb0ba9c-01aa-4e87-8e42-e88e2d84c42c",
            "05 November 2020",
            AnnouncementImportance.URGENT,
            "beb0ba9c-01aa-4e87-8e42-e88e2d84c42c",
            "stringeeee",
            emptyList()
        )

        val ANNOUNCEMENTS_LIST_BO = Collections.unmodifiableList(
            ArrayList<Announcement>().apply {
                add(ANNOUNCEMENT_BO)
                add(ANNOUNCEMENT_BO2)
            }
        )


        val ANNOUNCEMENT_DETAILS_BO = Announcement(
            false,
            "https://ijusoorusermgmtsit.channels.com.sa/api/UserInfo/GetUserImageByUserId?email=xXHDWVOvbpY3NROUlWbDVkMWFXcDFjMjl2Y21SbGJXOWhRR05vWVc1dVpXeHpMbU52YlM1ellRPT1mcTBCMQ==5n2ps",
            "05 November 2020",
            AnnouncementImportance.URGENT,
            "ae9077a7-f9b6-4e4f-8f57-2f848979d3bd",
            "string123",
            ArrayList<Attachment>().apply {
                add(ATTACHMENT_DETAILS_BO)
            }
        )

        val ATTACHMENT_DETAILS_BO = Attachment(
            "bd1f6385-c23d-49c0-987d-cc45ca71fd35",
            attachmentName = "string123"
        )
    }
}