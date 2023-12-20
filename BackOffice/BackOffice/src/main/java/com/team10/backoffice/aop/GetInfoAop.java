package com.team10.backoffice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class GetInfoAop {

	@Pointcut( "execution( * com.team10.backoffice.domain.*.*.*.*(..) )" )
	private void AllController(){}

	@Before( "AllController()" )
	public void execute( JoinPoint joinPoint ) {
		Object[] args = joinPoint.getArgs();
		log.info( "Aspect : " + Arrays.toString( args ) );
	}

}
