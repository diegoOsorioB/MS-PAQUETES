package MS_PAQUETES.paquetes.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil=new JwtUtil();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse  response,
                                    FilterChain chain)
            throws ServletException, IOException
    {
        String path=request.getRequestURI();
        String method=request.getMethod();

        if (path.equals("/api/paquetes")
                && method.equals("GET"))
        {
            chain.doFilter(request,response);
            return;
        }

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer "))
        {
            try {
                String jwt = token.substring(7).trim();
                String correo = jwtUtil.getUsernameFromToken(jwt);
                jwtUtil.validarToken(jwt);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(correo, null, Collections.emptyList());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }catch (Exception ex)
            {
                enviarError(response, HttpServletResponse.SC_UNAUTHORIZED, "Falta token de autorización");
                return;
            }
        }else
        {
            enviarError(response, HttpServletResponse.SC_UNAUTHORIZED, "Falta token de autorización");
            return;
        }
        chain.doFilter(request, response);
    }

    private void enviarError(HttpServletResponse res, int status, String mensaje) throws IOException {
        res.setStatus(status);
        res.setContentType("application/json");

        Map<String, Object> body = new HashMap<>();
        body.put("origen", "MS-Paquetes");
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("error", obtenerMensajeHttp(status));
        body.put("message", mensaje);

        res.getWriter().write(objectMapper.writeValueAsString(body));
    }

    private String obtenerMensajeHttp(int status) {
        switch (status) {
            case 400: return "Bad Request";
            case 401: return "Unauthorized";
            case 403: return "Forbidden";
            case 404: return "Not Found";
            case 500: return "Internal Server Error";
            default: return "Error";
        }
    }
}
