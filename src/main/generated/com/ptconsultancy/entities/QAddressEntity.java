package com.ptconsultancy.entities;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAddressEntity is a Querydsl query type for AddressEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAddressEntity extends EntityPathBase<AddressEntity> {

    private static final long serialVersionUID = 601007162L;

    public static final QAddressEntity addressEntity = new QAddressEntity("addressEntity");

    public final StringPath addressLine1 = createString("addressLine1");

    public final StringPath addressLine2 = createString("addressLine2");

    public final StringPath addressLine3 = createString("addressLine3");

    public final StringPath addressLine4 = createString("addressLine4");

    public final StringPath country = createString("country");

    public final StringPath county = createString("county");

    public final StringPath houseNameNumber = createString("houseNameNumber");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath postCode = createString("postCode");

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QAddressEntity(String variable) {
        super(AddressEntity.class, forVariable(variable));
    }

    public QAddressEntity(Path<? extends AddressEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAddressEntity(PathMetadata metadata) {
        super(AddressEntity.class, metadata);
    }

}

