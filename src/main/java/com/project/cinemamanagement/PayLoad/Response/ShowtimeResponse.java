package com.project.cinemamanagement.PayLoad.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeResponse {

    private Long showTimeId;
    private Date timeStart;
    private Date timeEnd;
    private int status;
    private Long movieId;
    private Long roomId;
}
