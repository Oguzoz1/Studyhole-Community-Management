export interface LoginResponse{
    authToken: string;
    refreshToken: string;
    username: string;
    expiresAt: Date;
}