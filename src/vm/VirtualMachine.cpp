#include <fstream>
#include "VirtualMachine.h"
#include "Frame.h"

using namespace std;

// #define ON_DEBUG
#ifdef ON_DEBUG
  #define DEBUG_OUT(message, target) cout << "debug | " << message << target << endl
#else
  #define DEBUG_OUT(message, target)
#endif

uint32_t VirtualMachine::load(string file_name) {
    input.clear();
    position = 0;
    file_name += ".wc";
    ifstream fin;
    fin.open(file_name.c_str(), ios::in | ios::binary);
    if (!fin) {
        cout << "error | file `"<< file_name.c_str() <<"` didn't open" << endl;
        exit(1);
    }
    while (!fin.eof()) {
        uint8_t byte = 0;
        fin.read((char*) &byte, sizeof(uint8_t));
        input.push_back(byte);
    }
    fin.close();

    uint32_t magic = *read_int();
    DEBUG_OUT("magic : ", magic);
    if (magic != 0xdeadbeef) {
        cout << "error | magic is invalid" << endl;
    }

    uint32_t entry_point = *read_int();
    DEBUG_OUT("entry point : ", entry_point);

    uint32_t registers_size = *read_int();
    DEBUG_OUT("registers size : ", registers_size);
    registers = new Object_*[registers_size];

    uint32_t references_size = *read_int();
    DEBUG_OUT("references size : ", references_size);

    uint32_t functions_size = *read_int();
    DEBUG_OUT("functions size : ", functions_size);
    functions = new uint32_t[functions_size];
    functions = (uint32_t*) read(sizeof(uint32_t) * functions_size);

    int32_t instructions_size = *read_int();
    DEBUG_OUT("instructions size : ", instructions_size);
    instructions = new instruction[instructions_size];
    instructions = (instruction*) read(sizeof(instruction) * instructions_size);

    uint32_t constant_pool_size = *read_int();
    DEBUG_OUT("constant pool size : ", constant_pool_size);
    constant_pool = new Object_*[constant_pool_size];
    for (uint32_t i = 0; i < constant_pool_size; i++) {
        uint32_t tag = *read_int();
        uint32_t size;
        switch (tag) {
            case 0x0: // int
                constant_pool[i] = (Object_ *) *read_int();
                break;
            case 0x1: // string
                size = *read_int();
                constant_pool[i] = (Object_ *) read(size);
                break;
            default:
                cout << "error | illegal constant type `" << tag << "`" << endl;
                exit(1);
        }
    }

    return entry_point;
}

// direct threading
#if defined __GNUC__ || defined __clnag__ || defined __INTEL_COMPILER
  #define DIRECT_THREADED
#endif

#ifdef DIRECT_THREADED
  #define INIT_DISPATCH JUMP;
  #define CASE(op) L_ ## op:
  #define NEXT i=*++pc; goto *table[i.type]
  #define JUMP i=*pc; goto *table[i.type]
  #define END_DISPATCH
#else
  #define INIT_DISPATCH while(true) { i = *pc; switch (i.type) {
  #define CASE(op) case op:
  #define NEXT pc++; break;
  #define JUMP break
  #define END_DISPATCH }}
#endif

void VirtualMachine::execute(uint32_t entry_point) {
    instruction* pc = instructions + functions[entry_point];
    instruction i;
#ifdef DIRECT_THREADED
    static void* table[] = {
        &&L_NOP
    };
#endif
    INIT_DISPATCH {
        CASE(NOP) {
            DEBUG_OUT("nop called with instruction : ", (uint32_t) pc->type);
        } NEXT;
    END_DISPATCH;

}

uint8_t* VirtualMachine::read(const int32_t length) {
    auto bytes = new uint8_t[length];
    for (int i = 0; i < length; i++) {
        bytes[i] = input[position + i];
    }
    position += length;
    return bytes;
}

uint32_t* VirtualMachine::read_int() {
    auto bytes = read(4);
    DEBUG_OUT("read_int called and returned : ", *(uint32_t*)bytes);
    return (uint32_t*)bytes;
}

#undef DEBUG_OUT
