package io.github.brightloong.workhour;

import io.github.brightloong.workhour.ui.MainFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"io.github.brightloong.workhour"})
public class WorkhourApplication {
    
    private static ConfigurableApplicationContext appCtx;
    
    @Autowired
    private MainFrame mainFrame;

	public static void main(String[] args) {
	 // 自定义Spring的配置builder,去除默认的headless和web集成相关功能
        appCtx = new SpringApplicationBuilder(WorkhourApplication.class)
                .headless(false)
                .web(false)
                .run(args);
        
	}
	
	@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	    return new CommandLineRunner() {
            @Override
            public void run(String... arg0) throws Exception {
                mainFrame.init();
            }
        };
    }
	
	/**
     * 读取配置的bean对象
     * @param name 对象名
     * @return
     */
    public static Object getBean(String name){
        return appCtx.getBeanFactory().getBean(name);
    }
}
