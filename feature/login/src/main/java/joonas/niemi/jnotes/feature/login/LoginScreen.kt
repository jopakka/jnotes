package joonas.niemi.jnotes.feature.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.forEachTextValue
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import joonas.niemi.jnotes.core.designsystem.component.JButton
import joonas.niemi.jnotes.core.designsystem.component.JTextField
import joonas.niemi.jnotes.core.designsystem.theme.JNotesTheme

private const val LOGIN_ROUTE = "login"
private const val SIGNUP_ROUTE = "signup"

private const val startRoute = LOGIN_ROUTE

private val navigationOptions = navOptions {
    popUpTo(startRoute)
    launchSingleTop = true
}

private fun NavHostController.navigateToLogin(navOptions: NavOptions? = navigationOptions) {
    navigate(LOGIN_ROUTE, navOptions)
}

private fun NavHostController.navigateToSignup(navOptions: NavOptions? = navigationOptions) {
    navigate(SIGNUP_ROUTE, navOptions)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = LOGIN_ROUTE) {
        composable(LOGIN_ROUTE) {
            LoginViewContent(
                email = viewModel.email,
                password = viewModel.password,
                modifier = modifier,
                onLogin = viewModel::onLogin,
                onToSignup = {
                    navController.navigateToSignup()
                    viewModel.clearSignupFields()
                },
                signingState = uiState.signingState,
            )
        }
        composable(SIGNUP_ROUTE) {
            SignupViewContent(
                email = viewModel.email,
                password = viewModel.password,
                confirmPassword = viewModel.confirmPassword,
                modifier = modifier,
                onSignup = viewModel::onSignup,
                onToLogin = {
                    navController.navigateToLogin()
                    viewModel.clearSignupFields()
                },
                onIsEmailValid = viewModel::isEmailValid,
                onIsPasswordValid = viewModel::isPasswordValid,
                signingState = uiState.signingState,
            )
        }
    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalComposeUiApi::class,
)
@Composable
private fun LoginViewContent(
    email: TextFieldState,
    password: TextFieldState,
    onLogin: () -> Unit,
    onToSignup: () -> Unit,
    signingState: SigningState?,
    modifier: Modifier = Modifier,
) {
    val (focusRequester) = FocusRequester.createRefs()
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }
    val isLoading = remember(signingState) { signingState is SigningState.Loading }

    LoginContainer(modifier) {
        FormContainer {
            FormTitle(
                text = stringResource(R.string.feature_login_login),
            )
            JTextField(
                state = email,
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.feature_login_email),
                placeholder = stringResource(R.string.feature_login_email_placeholder),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                ),
                onSubmit = { focusRequester.requestFocus() },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
            )
            PasswordField(
                state = password,
                onSubmit = { focusManager.clearFocus() },
                setShowPassword = { showPassword = it },
                showPassword = showPassword,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
            )

            if (signingState is SigningState.Error) {
                ErrorMessage(text = stringResource(signingState.messageRes))
            }

            JButton(
                onClick = onLogin,
                label = stringResource(R.string.feature_login_login),
                loading = isLoading,
                enabled = !isLoading,
            )
        }

        TextButton(onClick = onToSignup, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = stringResource(R.string.feature_login_create_account))
        }
    }
}

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class,
)
@Composable
private fun SignupViewContent(
    email: TextFieldState,
    password: TextFieldState,
    confirmPassword: TextFieldState,
    onSignup: () -> Unit,
    onToLogin: () -> Unit,
    onIsEmailValid: (String) -> Boolean,
    onIsPasswordValid: (String) -> Boolean,
    signingState: SigningState?,
    modifier: Modifier = Modifier,
) {
    val (pwFocusRequester, cpwFocusRequester) = FocusRequester.createRefs()
    val focusManager = LocalFocusManager.current
    var showPassword by remember { mutableStateOf(false) }
    var showConfirmPassword by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var emailValid by remember { mutableStateOf(false) }
    var passwordValid by remember { mutableStateOf(false) }
    var confirmPasswordValid by remember { mutableStateOf(false) }
    val isLoading = remember(signingState) { signingState is SigningState.Loading }

    val passwordValue by password.textAsFlow().collectAsStateWithLifecycle(password.text)
    val confirmPasswordValue by confirmPassword.textAsFlow()
        .collectAsStateWithLifecycle(confirmPassword.text)

    LaunchedEffect(email) {
        email.forEachTextValue {
            val isValid = onIsEmailValid(email.text.trim().toString())
            emailValid = isValid
            if (isValid) {
                emailError = false
            }
        }
    }

    LaunchedEffect(passwordValue) {
        val isValid = onIsPasswordValid(passwordValue.toString())
        passwordValid = isValid
        if (isValid) {
            passwordError = false
        }
    }

    LaunchedEffect(confirmPasswordValue, passwordValue) {
        val isValid = confirmPasswordValue.isNotEmpty() && passwordValue == confirmPasswordValue
        confirmPasswordValid = isValid
        if (isValid) {
            confirmPasswordError = false
        }
    }

    val checkInputs: () -> Boolean = {
        emailError = false
        passwordError = false
        confirmPasswordError = false
        var hasErrors = false

        if (!emailValid) {
            emailError = true
            hasErrors = true
        }

        if (!passwordValid) {
            passwordError = true
            hasErrors = true
        }

        if (!confirmPasswordValid) {
            confirmPasswordError = true
            hasErrors = true
        }

        hasErrors
    }

    LoginContainer(modifier) {
        FormContainer {
            FormTitle(
                text = stringResource(R.string.feature_login_create_account),
            )
            JTextField(
                state = email,
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(R.string.feature_login_email),
                placeholder = stringResource(R.string.feature_login_email_placeholder),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Email,
                ),
                onSubmit = { pwFocusRequester.requestFocus() },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = null)
                },
                error = emailError,
            )
            PasswordField(
                state = password,
                onSubmit = { cpwFocusRequester.requestFocus() },
                setShowPassword = { showPassword = it },
                showPassword = showPassword,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(pwFocusRequester),
                error = passwordError,
            )
            PasswordField(
                state = confirmPassword,
                onSubmit = { focusManager.clearFocus() },
                setShowPassword = { showConfirmPassword = it },
                showPassword = showConfirmPassword,
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(cpwFocusRequester),
                error = confirmPasswordError,
                label = stringResource(R.string.feature_login_confirm_password),
                placeholder = stringResource(R.string.feature_login_confirm_password),
            )

            if (signingState is SigningState.Error) {
                ErrorMessage(text = stringResource(signingState.messageRes))
            }

            JButton(
                onClick = {
                    if (!checkInputs()) {
                        onSignup()
                    }
                },
                label = stringResource(R.string.feature_login_signup),
                loading = isLoading,
                enabled = !isLoading,
            )
        }

        TextButton(onClick = onToLogin, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = stringResource(R.string.feature_login_already_have_account))
        }
    }
}

@Composable
private fun LoginContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        content = content,
    )
}

@Composable
private fun BoxScope.FormContainer(
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content,
    )
}

@Composable
private fun FormTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium,
    )
}

@Composable
private fun ErrorMessage(text: String) {
    Text(text = text, color = MaterialTheme.colorScheme.error)
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun PasswordField(
    state: TextFieldState,
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit,
    setShowPassword: (Boolean) -> Unit,
    showPassword: Boolean,
    error: Boolean = false,
    label: String = stringResource(R.string.feature_login_password),
    placeholder: String = stringResource(R.string.feature_login_password_placeholder),
) {
    JTextField(
        state = state,
        secured = !showPassword,
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        onSubmit = onSubmit,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
        leadingIcon = {
            Icon(Icons.Default.Lock, contentDescription = null)
        },
        trailingIcon = {
            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
                IconButton(
                    onClick = {
                        setShowPassword(!showPassword)
                    },
                ) {
                    if (showPassword) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_visibility_24),
                            contentDescription = null,
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.baseline_visibility_off_24),
                            contentDescription = null,
                        )
                    }
                }
            }
        },
        error = error,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewLoginViewContent() {
    JNotesTheme {
        LoginViewContent(
            email = rememberTextFieldState(),
            password = rememberTextFieldState(),
            onLogin = {},
            onToSignup = {},
            signingState = null,
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
private fun PreviewSignupViewContent() {
    JNotesTheme {
        SignupViewContent(
            email = rememberTextFieldState(),
            password = rememberTextFieldState(),
            confirmPassword = rememberTextFieldState(),
            onSignup = {},
            onToLogin = {},
            signingState = SigningState.Error(R.string.feature_login_login_error_invalid_credentials),
            onIsEmailValid = { true },
            onIsPasswordValid = { true },
        )
    }
}