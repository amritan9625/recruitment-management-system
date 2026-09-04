export function getRoleFromToken(token) {
    if (!token) {
        return null;
    }

    try {
        const payload = JSON.parse(
            atob(token.split(".")[1])
        );

        const authorities = payload.authorities;

        if (!authorities || authorities.length === 0) {
            return null;
        }

        const authority = authorities[0];

        return authority.startsWith("ROLE_")
            ? authority.substring(5)
            : authority;

    } catch (error) {
        console.error("Invalid JWT token", error);
        return null;
    }
}