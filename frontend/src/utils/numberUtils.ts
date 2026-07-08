export function parseBrazilianNumber(value: string): number {
    if (!value.trim()) {
        return 0;
    }

    return Number(value);
}