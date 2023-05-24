package com.retarus.fax.base.bulkoperation.status;


import com.retarus.fax.base.common.Property;
import com.retarus.fax.base.responses.status.RecipientStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class RecipientStatusTest {

    @Test
    void testGettersAndSetters() {
        String number = "+49891234678";
        List<Property> properties = new ArrayList<>();
        String status = "sent";
        String reason = "success";
        String sentTs = "2022-03-22T14:55:30Z";
        Integer durationInSecs = 120;
        String sentToNumber = "+49891234567";
        String remoteCsid = "1234567890";

        RecipientStatus recipientStatus = RecipientStatus.builder()
                .number(number)
                .properties(properties)
                .status(status)
                .reason(reason)
                .sentTimestamp(sentTs)
                .durationInSeconds(durationInSecs)
                .sentToNumber(sentToNumber)
                .remoteCsid(remoteCsid)
                .build();

        Assertions.assertEquals(number, recipientStatus.getRecipientNumber());
        Assertions.assertEquals(properties, recipientStatus.getPropertiesList());
        Assertions.assertEquals(status, recipientStatus.getStatus());
        Assertions.assertEquals(reason, recipientStatus.getReason());
        Assertions.assertEquals(sentTs, recipientStatus.getSentTimestamp());
        Assertions.assertEquals(durationInSecs, recipientStatus.getDurationInSeconds());
        Assertions.assertEquals(sentToNumber, recipientStatus.getSentToNumber());
        Assertions.assertEquals(remoteCsid, recipientStatus.getRemoteCsid());
    }

    @Test
    void testEqualsAndHashCode() {
        String number = "+49891234678";
        List<Property> properties = new ArrayList<>();
        String status = "sent";
        String reason = "success";
        String sentTs = "2022-03-22T14:55:30Z";
        Integer durationInSecs = 120;
        String sentToNumber = "+49891234567";
        String remoteCsid = "1234567890";

        RecipientStatus recipientStatus1 = RecipientStatus.builder()
                .number(number)
                .properties(properties)
                .status(status)
                .reason(reason)
                .sentTimestamp(sentTs)
                .durationInSeconds(durationInSecs)
                .sentToNumber(sentToNumber)
                .remoteCsid(remoteCsid)
                .build();

        RecipientStatus recipientStatus2 = RecipientStatus.builder()
                .number(number)
                .properties(properties)
                .status(status)
                .reason(reason)
                .sentTimestamp(sentTs)
                .durationInSeconds(durationInSecs)
                .sentToNumber(sentToNumber)
                .remoteCsid(remoteCsid)
                .build();

        RecipientStatus recipientStatus3 = RecipientStatus.builder()
                .number("+49891234567")
                .properties(properties)
                .status(status)
                .reason(reason)
                .sentTimestamp(sentTs)
                .durationInSeconds(durationInSecs)
                .sentToNumber(sentToNumber)
                .remoteCsid(remoteCsid)
                .build();

        Assertions.assertEquals(recipientStatus1, recipientStatus2);
        Assertions.assertNotEquals(recipientStatus1, recipientStatus3);
        Assertions.assertEquals(recipientStatus1.hashCode(), recipientStatus2.hashCode());
        Assertions.assertNotEquals(recipientStatus1.hashCode(), recipientStatus3.hashCode());
    }
}
