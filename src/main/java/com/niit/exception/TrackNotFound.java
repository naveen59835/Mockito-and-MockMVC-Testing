/*
 * Author : Naveen Kumar
 * Date : 30-01-2023
 * Created With : IntelliJ IDEA Community Edition
 */

package com.niit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus (code = HttpStatus.NOT_FOUND,reason = "Track Not Found")
public class TrackNotFound extends Exception{

}
