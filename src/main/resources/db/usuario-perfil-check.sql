ALTER TABLE public.usuario
ADD CONSTRAINT chk_usuario_perfil
CHECK (
    perfil IS NOT NULL
    AND perfil IN ('ADMIN', 'USER', 'SYSTEM')
    AND (
        perfil <> 'SYSTEM'
        OR COALESCE(cargo, '') = 'SYSADMIN'
    )
);
