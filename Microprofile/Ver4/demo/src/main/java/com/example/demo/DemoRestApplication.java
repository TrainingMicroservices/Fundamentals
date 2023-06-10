package com.example.demo;

import org.eclipse.microprofile.auth.LoginConfig;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 *
 */

@ApplicationPath("/coyote")

@LoginConfig(authMethod = "MP-JWT", realmName = "REALM_EXTERNAL_PROVIDERS")
@DeclareRoles({ "ROL_ADMIN","ROLE_PROVIDER_HSBC"})

public class DemoRestApplication extends Application {
}
