package com.github.manerajona.sendgrid

import com.sendgrid.Method
import com.sendgrid.Request
import com.sendgrid.SendGrid
import com.sendgrid.helpers.mail.Mail
import com.sendgrid.helpers.mail.objects.Content
import com.sendgrid.helpers.mail.objects.Email
import com.sendgrid.helpers.mail.objects.Personalization
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class SendGridClient(private val sendGrid: SendGrid) {

    private val log = KotlinLogging.logger {}

    @Value("\${email.sendgrid.template}")
    private lateinit var templateId: String

    fun sendPlainText(from: String, to: String, subject: String, body: String) {
        val mail = Mail(
            Email(from),
            subject,
            Email(to),
            Content("text/plain", body)
        )
        send(mail)
    }

    fun sendPersonalized(params: PersonalizationParams) {
        val personalization = Personalization()
        personalization.addDynamicTemplateData("name", params.name)
        personalization.addDynamicTemplateData("image", params.image)
        personalization.addDynamicTemplateData("text", params.text)
        personalization.addDynamicTemplateData("legal", params.legal)
        personalization.addTo(Email(params.to))

        val mail = Mail()
        mail.setFrom(Email(params.from))
        mail.setReplyTo(Email(params.replyTo))
        mail.setTemplateId(templateId)
        mail.addPersonalization(personalization)

        send(mail)
    }

    private fun send(mail: Mail) = try {
        val request = Request()
        request.method = Method.POST
        request.endpoint = "mail/send"
        request.body = mail.build()
        sendGrid.api(request)
    } catch (ex: IOException) {
        log.error("Error sending email", ex)
        throw RuntimeException(ex)
    }

}
