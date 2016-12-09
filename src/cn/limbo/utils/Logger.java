package cn.limbo.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 把这个类声明为切面:需要该类放入IOC容器中,再声明为一个切面
 * Created by limbo on 2016/12/8.
 */

@Order(0)//指定切面优先级,只越小优先级越高
@Aspect
@Component
public class Logger {

	/**
	 * 定义一个方法,用于声明切入点表达式,一般的,该方法中再不需要添加其他代码
	 * 主要是为了重用路径,使用@Pointcut来声明切入点表达式
	 * 后面的其他通知直接使用方法名来引用当前的切入点表达式
	 */
	//该包下任意返回值,任意类,任意方法,任意参数类型
	@Pointcut("execution(* cn.limbo.service.UserService.*(..))")
	public void declareJointPointExpression() {
	}

	/**
	 * 声明该方法是一个前置通知:在目标方法之前执行
	 *
	 * @param joinPoint 切入点
	 */
	@Before("declareJointPointExpression()")
	public void beforeMethod(JoinPoint joinPoint) {

		String methodName = joinPoint.getSignature().getName();
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		System.out.println("The Method " + methodName + "Begin With" + args);

	}

	/**
	 * 在目标方法执行之后执行,无论这个方法是否出错
	 * 在后置通知中还不能访问目标方法的返回值,只能通过返回通知访问
	 *
	 * @param joinPoint 切入点
	 */
	@After("declareJointPointExpression()")
	public void after(JoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().getName();
		System.out.println("Method " + methodName + "Ends");
	}


	/**
	 * 在方法正常结束后执行的代码
	 * 返回通知是可以访问到方法的返回值
	 *
	 * @param joinPoint 切入点
	 * @param result    返回值
	 */
	@AfterReturning(value = "declareJointPointExpression()", returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {

		String methodName = joinPoint.getSignature().getName();
		System.out.println("Method " + methodName + "return " + result);
	}


	/**
	 * 在目标方法出现异常的时候执行代码
	 * 可以访问异常对象,且可以指定出现特定异常时再执行通知
	 *
	 * @param joinPoint 切入点
	 * @param ex        抛出的异常
	 */
	@AfterThrowing(value = "declareJointPointExpression()", throwing = "ex")
	public void afterThrowing(JoinPoint joinPoint, Exception ex) { //Exception 可以改成 NullPointerException等特定异常
		String methodName = joinPoint.getSignature().getName();
		System.out.println("The Method " + methodName + " Occurs With " + ex);
	}


	/**
	 * 环绕通知需要ProceedingJoinPoint 类型参数 功能最强大
	 * 环绕通知类似于动态代理的全过程:ProceedingJoinPoint 类型的参数可以决定是否执行目标方法
	 * 且环绕通知必须有返回值,返回值即为目标方法的返回值
	 *
	 * @param proceedingJoinPoint 切入点
	 * @return
	 */
	@Around("declareJointPointExpression()")
	public Object aroundMethod(ProceedingJoinPoint proceedingJoinPoint) {

		Object result = null;
		String methodName = proceedingJoinPoint.getSignature().getName();

		//前置通知
		System.out.println("The Method " + methodName + " Begins With " + Arrays.asList(proceedingJoinPoint.getArgs()));

		try {
			result = proceedingJoinPoint.proceed();
		} catch (Throwable throwable) {
			//异常通知
			throwable.printStackTrace();
		}
		// 返回通知
		System.out.println("The Method " + methodName + " Ends With " + result);
		System.out.println("The Method " + methodName + " Ends ");
		return result;
	}
}
