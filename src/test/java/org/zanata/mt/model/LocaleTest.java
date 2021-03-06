package org.zanata.mt.model;

import org.junit.Test;
import org.zanata.mt.api.dto.LocaleId;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alex Eng<a href="mailto:aeng@redhat.com">aeng@redhat.com</a>
 */
public class LocaleTest {

    @Test
    public void testEmptyConstructor() {
        Locale locale = new Locale();
    }

    @Test
    public void testConstructor() {
        Locale locale = new Locale(LocaleId.EN, "English");
        assertThat(locale.getLocaleId()).isEqualTo(LocaleId.EN);
        assertThat(locale.getName()).isEqualTo("English");
    }

    @Test
    public void testEqualsAndHashcode() {
        Locale locale1 = new Locale(LocaleId.EN, "English");
        Locale locale2 = new Locale(LocaleId.EN, "English");

        assertThat(locale1.hashCode()).isEqualTo(locale2.hashCode());
        assertThat(locale1.equals(locale2)).isTrue();

        // change name, only localeId used for comparison
        locale2 = new Locale(LocaleId.EN, "English US");
        assertThat(locale1.hashCode()).isEqualTo(locale2.hashCode());
        assertThat(locale1.equals(locale2)).isTrue();

        // change localeId
        locale2 = new Locale(LocaleId.EN_US, "English");
        assertThat(locale1.hashCode()).isNotEqualTo(locale2.hashCode());
        assertThat(locale1.equals(locale2)).isFalse();

        // diff type
        String test = "test";
        assertThat(locale1.hashCode()).isNotEqualTo(test.hashCode());
        assertThat(locale1.equals(test)).isFalse();
    }
}
