package rs.ac.uns.ftn.Bookify.model;

enum NotificationType {
    RESERVATION_CREATED,
    RESERVATION_CANCELED,
    NEW_USER_RATING,
    NEW_ACCOMMODATION_RATING,
    RESERVATION_RESPONSE
}

enum Status {
    ACCEPTED,
    PENDING,
    CANCELED,
    REJECTED
}

enum AccommodationType {
    ROOM,
    HOTEL,
    APARTMENT
}

enum Filter {
    FREE_WIFI,
    FREE_PARKING,
    AIR_CONDITIONING,
    FAMILY_ROOMS,
    FRONT_DESK,
    BAR,
    JACUZZI,
    SAUNA,
    HEATING,
    LUGGAGE_STORAGE,
    BREAKFAST,
    LUNCH,
    DINER,
    AIRPORT_SHUTTLE,
    PRIVATE_BATHROOM,
    WHEELCHAIR,
    DEPOSIT_BOX,
    NON_SMOKING,
    CITY_CENTER,
    TERRACE,
    SWIMMING_POOL,
    GARDEN
}

enum PricePer {
    PERSON,
    ROOM
}