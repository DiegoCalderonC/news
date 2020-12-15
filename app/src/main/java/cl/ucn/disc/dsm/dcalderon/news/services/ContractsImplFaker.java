/*
 * Copyright Staday.year Diego.Calderon.Carvajal, diego.calderon@alumnos.ucn.cl
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package cl.ucn.disc.dsm.dcalderon.news.services;


import com.github.javafaker.Faker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cl.ucn.disc.dsm.dcalderon.news.model.News;
import cl.ucn.disc.dsm.dcalderon.news.model.Validation;

/**
 * @author Diego Calderon-Carvajal.
 */
public final class ContractsImplFaker implements Contracts {

    /**
     * The Logger.
     */
    private static final Logger log = LoggerFactory.getLogger(ContractsImplFaker.class);

    /**
     * The List of News.
     */
    private final List<News> theNews = new ArrayList<>();

    /**
     * The Constructor: Generate 5 {@link News}.
     */
    public ContractsImplFaker() {

        // Thee faker to use
        final Faker faker = Faker.instance();

        for (int i = 0; i < 5; i++) {
            this.theNews.add(new News(
                    faker.book().title(),
                    faker.name().username(),
                    faker.name().fullName(),
                    faker.internet().url(),
                    faker.internet().avatar(),
                    faker.harryPotter().quote(),
                    faker.lorem().paragraph(3),
                    ZonedDateTime.now(ZoneId.of("-3"))
            ));
        }
    }

    /**
     * Get the list of News.
     *
     * @param size size of the list
     * @return the List of News
     */
    @Override
    public List<News> retrieveNews(final Integer size) {
        // Return all the data
        if (size > theNews.size()) {
            return Collections.unmodifiableList(this.theNews);
        }
        // The last "size" elements.
        return Collections.unmodifiableList(
                theNews.subList(theNews.size() - size, theNews.size())
        );
    }

    /**
     * Save one News into the System. Don't allow repeated.
     *
     * @param news to save.
     */
    @Override
    public void saveNews(final News news) {
        // Nullity
        Validation.notNull(news, "news");

        // Check duplicates
        for (News n : this.theNews) {
            if (n.getId().equals(news.getId())) {
                throw new IllegalArgumentException("Can't allow duplicate news!");
            }
        }

        // Add news
        this.theNews.add(news);
    }
}
