/*
 * @Denisenko Artur
 */

package ru.javawebinar.topjava.filter;

import java.util.List;

public interface ModelFilter<T, K> {
    List<T> meetCriteria(List<T> t, K k);
}
