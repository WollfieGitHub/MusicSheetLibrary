package fr.wollfie.sheetmusiclibrary.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.io.serialization.JsonSerializable;

import java.nio.channels.FileChannel;
import java.util.*;
import java.util.random.RandomGenerator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class LingualString implements JsonSerializable {
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       FIELDS                                         ||
// ||                                                                                      ||
// \\======================================================================================//
    
    private Map<Language, String> translations;

    public Map<Language, String> getTranslations() { return translations; }
    
    public void setTranslations(Map<Language, String> translations) {
        this.translations = translations;
    }
    
    public void addTranslation(Language language, String translation) {
        this.translations.put(language, translation);
    }

// //======================================================================================\\
// ||                                                                                      ||
// ||                                       CONSTRUCTORS                                   ||
// ||                                                                                      ||
// \\======================================================================================//


    public LingualString() { }

    public LingualString(Map<Language, String> translations) {
        this.translations = translations;
    }
    
    @SafeVarargs public LingualString(Tuple<Language, String>... translations) {
        this(Arrays.stream(translations).collect(Collectors.toMap(Tuple::left, Tuple::right)));
    }
    
    public LingualString(String englishTranslation) {
        this(Tuple.of(Language.ENGLISH, englishTranslation));
    }
    
// //======================================================================================\\
// ||                                                                                      ||
// ||                                       GETTERS                                        ||
// ||                                                                                      ||
// \\======================================================================================//
    
    @JsonIgnore public Collection<String> getAllTranslations() {
        return this.translations.values();
    }

    @JsonIgnore public String getEnglishTranslation() {
        return this.translations.get(Language.ENGLISH);
    }

    @JsonIgnore public String getTranslationFor(Language language) {
        return this.translations.get(language);
    }
}
