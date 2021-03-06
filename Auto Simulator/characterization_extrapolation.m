A = [
    0.8678678537600736, 0.0071917452743623786;
    0.005903949588909425, 0.8723072041767262;
];

B = [
    0.050392385308988936, -0.004313538469407867;
    -0.002206154164037924, 0.047675029476658845;
];

sysd = ss(A, B, eye(2), zeros(2), 0.02);
sysc = d2c(sysd);

disp("Ac = ");
disp(sysc.A);
disp("Bc = ");
disp(sysc.B);

B2 = [
    4.0919915607596564E-4
    3.7658807561286766E-4
];

sysd2 = ss(A, B2, eye(2), zeros(1), 0.02);
sysc2 = d2c(sysd2);

%disp(sysc2.B);

Qd = [
    0.006307812352729869, 0.006149384611897019;
    0.006149384611897019, 0.006451202836649863;
];

nAd = inv(A);

M = [
    nAd, nAd*Qd;
    zeros(2, 2), A';
];

Mc = logm(M)/0.02;
Qc = Mc(1:2, 3:4);

disp("Qc = ");
disp(Qc);