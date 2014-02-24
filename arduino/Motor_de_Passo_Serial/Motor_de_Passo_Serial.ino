
/*

Exemplo de como controlar o sentido e velocidade do motor de passo
Grave o programa no seu Arduino e abra a Serial Monitor, botão com ícone de uma lupa no canto direito superior
Para mudar o sentido digite h para rodar no sentido horário ou digite a para rodar no sentido antihorário e pressione enter
Para aumentar a velocidade digite + e para diminuir - e pressione enter
Alimente o motor por um fotne externa de no mínimo 500mA de 5V.
A velocidade máxima real do motor é de 14,5 RPM.
A velocidade em rpm no programa não conrresponde com a real, pois a biblioteca do Arduino é imprecisa.
Conecte o motor da seguinte forma:
O pino IN 1 do motor de passo no Pino 8 do Arduino
O pino IN 2 do motor de passo no Pino 9 do Arduino
O pino IN 3 do motor de passo no Pino 10 do Arduino
O pino IN 4 do motor de passo no Pino 11 do Arduino
O pino VCC do motor de passo no Pino 5V da Fonte
O pino GND do motor de passo no Pino Terra da Fonte

O motor pode ser alimentado pelo próprio Arduino conectado na USB, mas devido a limitação de correte da USB a velocidade e torque do motor não é a máxima.
A velocidade média alcançada com o motor alimentado pela USB é em torno de 8 RPM.
Para conectar o motor diretamente no Arduino e sendo alimentado pelo Arduino conecte da seguinte forma:
O pino IN 1 do motor de passo no Pino 8 do Arduino
O pino IN 2 do motor de passo no Pino 9 do Arduino
O pino IN 3 do motor de passo no Pino 10 do Arduino
O pino IN 4 do motor de passo no Pino 11 do Arduino
O pino VCC do motor de passo no Pino 5V do Arduino
O pino GND do motor de passo no Pino GND do Arduino.

*/

#include <Stepper.h>

const int passosVoltaCompleta = 2037;  // numero de passos por volta completa fixo, não mudar
Stepper meuMotor(passosVoltaCompleta,8,10,11,9);

 //pinos que o motor esta conectado, veja que apesar de estar conectado em sequencia é preciso declarar de forma(IN1,IN3,IN4,IN2)

int v = 5;// determina a velocidade inicial para 5
int s = 10; //numero de passos ate verificar novamente a serial por uma entrada, valor positivo para antihorario e negativo para horario

void setup() {
  Serial.begin(9600);
  Serial.println("Digite a para sentido antihorario e h para sentido horario e pressione Enter");
  Serial.println("Digite + para aumentar a velocidade ou - para diminuir e pressione Enter");
  Serial.print("Velocidade atual: ");
  Serial.print(v);
  Serial.println(" RPM");
  Serial.println("Sentido atual: Antihorario");
  Serial.println("---------------------------------");
}

void loop() {
  if (Serial.available() > 0) {
    char caracter;
    caracter = Serial.read();
    if(caracter=='a')
     s = 10;
    if(caracter=='h')
     s = -10;
    if(caracter=='+')
     v++;
    if(caracter=='-'){
      if(v>1)
       v--;
    }
    Serial.print("Velocidade atual: ");
    Serial.print(v);
    Serial.println(" RPM");
    if(s>0)
    Serial.println("Sentido Antihorario");
    if(s<0)
    Serial.println("Sentido Horario");
    Serial.println("---------------------------------");
  }
  meuMotor.setSpeed(v);//determinando para o motor a velocidade da variável v
  meuMotor.step(s);//determinando para o motor o numero de passos da variável s
}

