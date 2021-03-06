package org.zanata.mt.dao;

import org.jglue.cdiunit.CdiRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.zanata.mt.JPATest;
import org.zanata.mt.api.dto.LocaleId;
import org.zanata.mt.model.Locale;
import org.zanata.mt.model.TextFlow;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alex Eng<a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
@RunWith(CdiRunner.class)
public class TextFlowDAOTest extends JPATest {

    private TextFlowDAO dao;
    private String hash;

    @Before
    public void setup() {
        dao = new TextFlowDAO(getEm());
    }

    @Test
    public void testEmptyConstructor() {
        TextFlowDAO dao = new TextFlowDAO();
    }

    @Test
    public void testGetByHashNull() {
        TextFlow tf = dao.getByHash(LocaleId.EN_US, "hash");
        assertThat(tf).isNull();
    }

    @Test
    public void testGetByHash() {
        TextFlow tf = dao.getByHash(LocaleId.EN_US, hash);
        assertThat(tf).isNotNull();
        assertThat(tf.getContent()).isEqualTo("content");
        assertThat(tf.getContentHash()).isEqualTo(hash);
    }

    @Override
    protected void setupTestData() {
        Locale locale = new Locale(LocaleId.EN_US, "English US");
        getEm().persist(locale);

        TextFlow tf = new TextFlow("content", locale);
        getEm().persist(tf);
        hash = tf.getContentHash();
    }
}
