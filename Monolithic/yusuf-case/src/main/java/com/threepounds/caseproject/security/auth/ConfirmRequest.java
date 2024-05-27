package com.threepounds.caseproject.security.auth;
        import lombok.AllArgsConstructor;
        import lombok.Builder;
        import lombok.Data;
        import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmRequest {
    private String userId;
    private String otp;
}