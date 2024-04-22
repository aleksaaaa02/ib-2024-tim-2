package com.bookify.pki.model;

import com.bookify.pki.enumerations.ExtensionsType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Extension {
    private ExtensionsType extensionsType;
    private Object value;

}
