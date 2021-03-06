package org.zanata.mt.service;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MediaType;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.zanata.mt.api.dto.LocaleId;
import org.zanata.mt.dao.TextFlowDAO;
import org.zanata.mt.dao.TextFlowTargetDAO;
import org.zanata.mt.model.Locale;
import org.zanata.mt.model.BackendID;
import org.zanata.mt.backend.ms.MicrosoftTranslatorBackend;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.zanata.mt.api.APIConstant.AZURE_KEY;

/**
 * @author Alex Eng <a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public class PersistentTranslationServiceTest {

    @Mock
    private TextFlowDAO textFlowDAO;

    @Mock
    private TextFlowTargetDAO textFlowTargetDAO;

    @Mock
    private MicrosoftTranslatorBackend msProvider;

    private PersistentTranslationService persistentTranslationService;

    @BeforeClass
    public static void beforeClass() {
        String secret = "subscriptionKey";
        System.setProperty(AZURE_KEY, secret);
    }

    @Before
    public void setup() {
        persistentTranslationService =
            new PersistentTranslationService(textFlowDAO, textFlowTargetDAO,
                msProvider);
    }

    @Test
    public void testEmptyConstructor() {
        persistentTranslationService = new PersistentTranslationService();
    }

    @Test
    public void testValidateEmptySrcLocale() {
        List<String> source = Lists.newArrayList("testing source");
        Locale targetLocale = new Locale(LocaleId.DE, "German");

        assertThatThrownBy(() -> persistentTranslationService.translate(source,
                null, targetLocale,
                BackendID.MS, MediaType.TEXT_PLAIN_TYPE))
                        .isInstanceOf(BadRequestException.class);
    }

    @Test
    public void testValidateEmptyTargetLocale() {
        List<String> source = Lists.newArrayList("testing source");
        Locale sourceLocale = new Locale(LocaleId.EN, "English");

        assertThatThrownBy(() -> persistentTranslationService.translate(source,
                sourceLocale, null,
                BackendID.MS, MediaType.TEXT_PLAIN_TYPE))
                        .isInstanceOf(BadRequestException.class);
    }

    @Test
    public void testValidateEmptyProvider() {
        List<String> source = Lists.newArrayList("testing source");
        Locale sourceLocale = new Locale(LocaleId.EN, "English");
        Locale targetLocale = new Locale(LocaleId.DE, "German");

        assertThatThrownBy(() -> persistentTranslationService.translate(source,
                sourceLocale, targetLocale, null, MediaType.TEXT_PLAIN_TYPE))
                        .isInstanceOf(BadRequestException.class);
    }
}
