package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import com.mycompany.myapp.domain.enumeration.TypePlat;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Plat entity. This class is used in PlatResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /plats?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PlatCriteria implements Serializable {
    /**
     * Class for filtering TypePlat
     */
    public static class TypePlatFilter extends Filter<TypePlat> {
    }

    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter name;

    private TypePlatFilter type;

    private StringFilter description;

    private LongFilter createurId;

    private LongFilter tagsId;

    public PlatCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public TypePlatFilter getType() {
        return type;
    }

    public void setType(TypePlatFilter type) {
        this.type = type;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LongFilter getCreateurId() {
        return createurId;
    }

    public void setCreateurId(LongFilter createurId) {
        this.createurId = createurId;
    }

    public LongFilter getTagsId() {
        return tagsId;
    }

    public void setTagsId(LongFilter tagsId) {
        this.tagsId = tagsId;
    }

    @Override
    public String toString() {
        return "PlatCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (createurId != null ? "createurId=" + createurId + ", " : "") +
                (tagsId != null ? "tagsId=" + tagsId + ", " : "") +
            "}";
    }

}
