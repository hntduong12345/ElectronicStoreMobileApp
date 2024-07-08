package com.example.electronicstoremobileapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PagingResponseDto<T> {
    @SerializedName("total")
    public int Total;
    @SerializedName("values")
    public List<T> Values;
}
