package br.com.dio.board.entity;

import java.util.stream.Stream;

public enum ColumnTypeEnum {

	INITIAL, CANCEL, FINAL, PENDING;

    public static ColumnTypeEnum findByName(final String name){
        return Stream.of(ColumnTypeEnum.values())
                .filter(b -> b.name().equals(name))
                .findFirst().orElseThrow();
    }

}